package com.ilyushenko.invoicebuilder.service;

import com.ilyushenko.invoicebuilder.entity.Invoice;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JasperReportServiceImpl implements JasperReportService {

    @Autowired
    private ResourceLoader resourceLoader;

    @SneakyThrows
    @Override
    public byte[] generatePDFReport(Invoice invoice) {
        Resource resource = resourceLoader.getResource("classpath:invoiceReport.jrxml");
        InputStream inputStream = resource.getInputStream();
        Map<String, Object> parameter = createParam(invoice);
        JasperReport jasperDesign = JasperCompileManager.compileReport(inputStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperDesign, parameter, new JREmptyDataSource());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
        log.info("Report Generated!");
        return byteArrayOutputStream.toByteArray();
    }

    private Map<String, Object> createParam(Invoice invoice) {
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("dateOfIssue", invoice.getDateOfIssue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        parameter.put("dateOfSale", invoice.getDateOfSale().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        parameter.put("sellerFullName", invoice.getSeller().getFullName());
        parameter.put("sellerVATCode", invoice.getSeller().getVatCode());
        parameter.put("sellerStreet", invoice.getSeller().getStreet());
        parameter.put("sellerCityPostCode", invoice.getSeller().getPostalCode() + " " + invoice.getSeller().getCity());

        parameter.put("buyerFullName", invoice.getBuyer().getName());
        parameter.put("buyerVATCode", invoice.getBuyer().getVatCode());
        parameter.put("buyerStreet", invoice.getBuyer().getStreet());
        parameter.put("buyerCityPostCode", invoice.getBuyer().getPostalCode() + " " + invoice.getBuyer().getCity());

        parameter.put("currentDocumentNameAndNumber", invoice.getCurrentDocumentName() + " " + invoice.getNumber());
        parameter.put("totalNetPrice", invoice.getRecipientWrapper().getTotalNetPrice());
        parameter.put("totalVATAmount", invoice.getRecipientWrapper().getTotalVATAmount());
        parameter.put("totalGrossPrice", invoice.getRecipientWrapper().getTotalGrossPrice());
        parameter.put("totalVATRate", "N/A");
        parameter.put("methodOfPaymentAndDay", invoice.getMethodOfPayment() + " " + invoice.getDueDate());
        parameter.put("dueDate", invoice.getDateOfIssue().plusDays(Integer.parseInt(invoice.getDueDate()))
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        parameter.put("accountNumber", invoice.getAccountNumber());
        parameter.put("currency", invoice.getRecipientWrapper().getCurrency().toUpperCase());
        parameter.put("totalDueWords", CashWordConverter.doubleConvert(invoice.getRecipientWrapper().getTotalDueNetPrice()));
        parameter.put("vatAccountNumber", invoice.getVATAccountNumber());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(invoice.getRecipientWrapper().getRecipients());
        parameter.put("recipientCollection", dataSource);
        return parameter;
    }
}
