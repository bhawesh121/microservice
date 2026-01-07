package com.programmingTech.order_service.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @NotEmpty(message = "orderLineItemsList cannot be empty")
    private List<OrderLineItemsDto> orderLineItemsList;
}
