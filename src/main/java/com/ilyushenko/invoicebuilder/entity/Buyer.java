package com.ilyushenko.invoicebuilder.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Buyer {
    @NotEmpty(message = "The full name is buyer.")
    @Size(min = 2, max = 100, message = "The length of full name must be between 2 and 100 characters.")
    private String name;
    @NotEmpty(message = "The street is required.")
    private String street;
    @NotEmpty(message = "The city is required.")
    private String city;
    @NotEmpty(message = "The postalCode is required.")
    private String postalCode;
    @NotEmpty(message = "The VAT code is required.")
    private String vatCode;
    private String signature;
}
