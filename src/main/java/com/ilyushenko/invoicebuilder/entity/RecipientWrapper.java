package com.ilyushenko.invoicebuilder.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecipientWrapper {
    private List<Recipient> recipients;
    private Double totalNetPrice;
    private Double totalVATAmount;
    private Double totalGrossPrice;
    private Double paid;
    private Double totalDueNetPrice;
    private String currency;
    private String exchange;
}
