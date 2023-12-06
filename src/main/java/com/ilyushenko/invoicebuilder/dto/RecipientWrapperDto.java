package com.ilyushenko.invoicebuilder.dto;

import com.ilyushenko.invoicebuilder.dto.RecipientDto;
import com.ilyushenko.invoicebuilder.entity.RecipientWrapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Data
public class RecipientWrapperDto {
    private List<RecipientDto> recipientsDto;
    @Value("${s:0.0}")
    private Double paid;
    @Value("${currency:EUR}")
    private String currency;
    @Value("${exchange:no}")
    private String exchange;

    public RecipientWrapper toRecipientWrapper() {
        return RecipientWrapper.builder().paid(paid).currency(currency).exchange(exchange).build();
    }
}
