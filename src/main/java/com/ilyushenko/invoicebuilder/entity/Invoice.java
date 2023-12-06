package com.ilyushenko.invoicebuilder.entity;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jdk.jfr.BooleanFlag;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Invoice {

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
    private RecipientWrapper recipientWrapper;
    @NotEmpty
    private String methodOfPayment;
    @NotEmpty
    private String dueDate;
    @NotEmpty
    private String accountNumber;
    @NotEmpty
    private String VATAccountNumber;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Invoice:").append("\n")
                .append("currentDocumentName=").append(currentDocumentName).append("\n")
                .append("number=").append(number).append("\n")
                .append("dateOfIssue=").append(dateOfIssue).append("\n")
                .append("sign=").append(sign).append("\n")
                .append("placeOfIssue=").append(placeOfIssue).append("\n")
                .append("dateOfSale=").append(dateOfSale).append("\n")
                .append("comments=").append(comments).append("\n")
                .append("seller=").append(seller).append("\n")
                .append("buyer=").append(buyer).append("\n")
                .append("recipientWrapperDto=").append(recipientWrapper).append("\n")
                .append("methodOfPayment=").append(methodOfPayment).append("\n")
                .append("dueDate=").append(dueDate).append("\n")
                .append("accountNumber=").append(accountNumber).append("\n")
                .append("VATAccountNumber=").append(VATAccountNumber).append("\n")
                .append("recipients=").append(recipientWrapper != null ? recipientWrapper.getRecipients() : null).append("\n")
                .append('}');
        return sb.toString();
    }
}
