package com.ilyushenko.invoicebuilder.service;

import com.ilyushenko.invoicebuilder.entity.Invoice;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface JasperReportService {

    public byte[] generatePDFReport(Invoice invoice);
}
