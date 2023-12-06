package com.ilyushenko.invoicebuilder.service;

import com.ilyushenko.invoicebuilder.dto.InvoiceDto;
import com.ilyushenko.invoicebuilder.entity.Invoice;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface InvoiceService {

    Invoice convertToInvoice(InvoiceDto invoiceDto);

    List<String> validate(BindingResult bindingResult);
}
