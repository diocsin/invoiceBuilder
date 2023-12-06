package com.ilyushenko.invoicebuilder.service;

import com.ilyushenko.invoicebuilder.dto.InvoiceDto;
import com.ilyushenko.invoicebuilder.dto.RecipientWrapperDto;
import com.ilyushenko.invoicebuilder.entity.Invoice;
import com.ilyushenko.invoicebuilder.entity.Recipient;
import com.ilyushenko.invoicebuilder.entity.RecipientWrapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Override
    public Invoice convertToInvoice(InvoiceDto invoiceDto) {
        RecipientWrapperDto recipientWrapperDto = invoiceDto.getRecipientWrapperDto();
        AtomicInteger no = new AtomicInteger();
        List<Recipient> recipients = recipientWrapperDto.getRecipientsDto().stream()
                .map(recipientDto -> {
                    Recipient recipient = recipientDto.toRecipient();
                    recipient.setNo(no.incrementAndGet());
                    recipient.setVatRate("N/A");
                    return recipient;
                })
                .toList();
        Double totalNetPriceSum = recipients.stream().mapToDouble(Recipient::getTotalNetPrice).sum();
        Double vatAmountSum = recipients.stream().mapToDouble(Recipient::getVatAmount).sum();
        Double totalGrossPriceSum = recipients.stream().mapToDouble(Recipient::getTotalGrossPrice).sum();
        double totalNetPrice = Double.parseDouble(new DecimalFormat("#.##").format(totalNetPriceSum));
        double vatAmount = Double.parseDouble(new DecimalFormat("#.##").format(vatAmountSum));
        double totalGrossPrice = Double.parseDouble(new DecimalFormat("#.##").format(totalGrossPriceSum));
        RecipientWrapper recipientWrapper = recipientWrapperDto.toRecipientWrapper();
        recipientWrapper.setTotalNetPrice(totalNetPrice);
        recipientWrapper.setTotalDueNetPrice(totalNetPrice);
        recipientWrapper.setTotalGrossPrice(totalGrossPrice);
        recipientWrapper.setTotalVATAmount(vatAmount);
        recipientWrapper.setRecipients(recipients);
        Invoice invoice = invoiceDto.toInvoice();
        invoice.setRecipientWrapper(recipientWrapper);
        return invoice;
    }

    @Override
    public List<String> validate(BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        return errors;
    }
}
