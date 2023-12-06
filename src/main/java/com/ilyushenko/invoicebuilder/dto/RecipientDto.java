package com.ilyushenko.invoicebuilder.dto;

import com.ilyushenko.invoicebuilder.entity.Recipient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class RecipientDto {
    @NotEmpty
    private String nameOfGoodsOrService;
    @NotEmpty
    @Value("${unit:svc}")
    private String unit;
    @NotNull
    @Value("${quantity:1}")
    private Integer quantity;
    @PositiveOrZero
    private Double unitNetPrice;
    @NotEmpty
    @Value("${vatRate:np.}")
    private String vatRate;
    @PositiveOrZero
    private Double totalGrossPrice;

    public Recipient toRecipient() {
        return Recipient.builder()
                .nameOfGoodsOrService(nameOfGoodsOrService)
                .unit(unit)
                .quantity(quantity)
                .unitNetPrice(unitNetPrice)
                .vatRate(vatRate)
                .vatAmount(0.0)
                .totalNetPrice(unitNetPrice)
                .totalGrossPrice(totalGrossPrice)
                .build();
    }
}
