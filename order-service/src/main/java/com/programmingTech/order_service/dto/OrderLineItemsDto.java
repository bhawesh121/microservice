package com.programmingTech.order_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLineItemsDto {

    private Long id;

    @NotBlank(message = "skuCode is required")
    private String skuCode;

    @NotNull(message = "price is required")
    private BigDecimal price;

    @NotNull(message = "quantity is required")
    private Integer quantity;
}
