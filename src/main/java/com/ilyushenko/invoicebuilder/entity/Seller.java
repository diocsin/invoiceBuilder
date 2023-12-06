package com.ilyushenko.invoicebuilder.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Seller {
    @NotEmpty
    @Size(min = 2, max = 100, message = "The length of full name must be between 2 and 100 characters.")
    private String fullName;
    @NotEmpty
    private String street;
    @NotEmpty
    private String city;
    @NotEmpty
    private String postalCode;
    @NotEmpty
    private String vatCode;
    @NotEmpty
    private String signature;
}
