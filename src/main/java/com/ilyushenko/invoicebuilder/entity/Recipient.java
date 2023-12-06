package com.ilyushenko.invoicebuilder.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Recipient {
    private int no;
    private String nameOfGoodsOrService;
    private String unit;
    private Integer quantity;
    private Double unitNetPrice;
    private Double totalNetPrice;
    private String vatRate;
    private Double vatAmount;
    private Double totalGrossPrice;
}
