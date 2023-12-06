package com.ilyushenko.invoicebuilder.dto;

import com.ilyushenko.invoicebuilder.entity.Buyer;
import com.ilyushenko.invoicebuilder.entity.Invoice;
import com.ilyushenko.invoicebuilder.entity.Seller;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InvoiceDto {

    @NotEmpty(message = "The currentDocumentName is required.")
    private String currentDocumentName;
    @NotEmpty
    private String number;
    @NotNull
    @FutureOrPresent
    private LocalDate dateOfIssue;
    @NotEmpty
    private String sign;
    @NotEmpty
    private String placeOfIssue;
    @PastOrPresent
    private LocalDate dateOfSale;
    private String comments;
    @NotNull
    private Seller seller;
    @NotNull
    private Buyer buyer;
    @NotNull
    private RecipientWrapperDto recipientWrapperDto;
    @NotEmpty
    private String methodOfPayment;
    @NotEmpty
    private String dueDate;
    @NotEmpty
    private String accountNumber;
    @NotEmpty
    private String VATAccountNumber;

    public Invoice toInvoice(){
        return Invoice.builder()
                .currentDocumentName(currentDocumentName)
                .number(number)
                .dateOfIssue(dateOfIssue)
                .sign(sign)
                .placeOfIssue(placeOfIssue)
                .dateOfSale(dateOfSale)
                .comments(comments)
                .seller(seller)
                .buyer(buyer)
                .methodOfPayment(methodOfPayment)
                .dueDate(dueDate)
                .accountNumber(accountNumber)
                .VATAccountNumber(VATAccountNumber)
                .build();
    }
}
