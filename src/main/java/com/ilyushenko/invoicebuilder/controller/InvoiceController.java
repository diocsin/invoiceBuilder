package com.ilyushenko.invoicebuilder.controller;

import com.ilyushenko.invoicebuilder.dto.InvoiceDto;
import com.ilyushenko.invoicebuilder.entity.Invoice;
import com.ilyushenko.invoicebuilder.service.InvoiceService;
import com.ilyushenko.invoicebuilder.service.JasperReportService;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private JasperReportService jasperReportService;

    @SneakyThrows
    @PostMapping("/build")
    private ResponseEntity<?> buildInvoice(@Valid @RequestBody InvoiceDto invoiceDto, BindingResult bindingResult) {
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors()) {
            List<String> errors = invoiceService.validate(bindingResult);
            String errorMessage = String.join(", ", errors);
            log.error(errorMessage);
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>(errorMessage, headers, HttpStatus.BAD_REQUEST);
        }
        try {
            Invoice invoice = invoiceService.convertToInvoice(invoiceDto);
            byte[] pdfContent = jasperReportService.generatePDFReport(invoice);
            log.info("Received request to build invoice: {}", invoice);
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "invoice.pdf");
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>("Error generating invoice: " + e.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
