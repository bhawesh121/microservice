//package com.programmingTech.order_service.service;
//
//import com.programmingTech.order_service.dto.InventoryResponse;
//import com.programmingTech.order_service.dto.OrderLineItemsDto;
//import com.programmingTech.order_service.dto.OrderRequest;
//import com.programmingTech.order_service.model.Order;
//import com.programmingTech.order_service.model.OrderLineItems;
//import com.programmingTech.order_service.repository.OrderRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class OrderService {
//
//    private final OrderRepository orderRepository;
//    private final WebClient webClient;
//
//    @Transactional
//    public void placeOrder(OrderRequest orderRequest) throws IllegalAccessException {
//        Order order = new Order();
//        order.setOrderNumber(UUID.randomUUID().toString());
//
//        List<OrderLineItems> orderlineItems = orderRequest.getOrderLineItemsList()
//                .stream()
//                .map(this::mapToDto).toList();
//
//        order.setOrderLineItemsList(orderlineItems);
//
//        List<String> skucodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();
//
//        InventoryResponse[] allResponse = webClient.get()
//                .uri("http://localhost:8082/api/inventory",
//                        uriBuilder -> uriBuilder.queryParam("skuCode", skucodes).build())
//                        .retrieve()
//                        .bodyToMono(InventoryResponse[].class)
//                        .block();
//
//        boolean allProductInStock = Arrays.stream(allResponse).allMatch(InventoryResponse::getIsInStock);
//
//        if(allProductInStock){
//            orderRepository.save(order);
//        }
//        else {
//            throw new IllegalAccessException("Order is not in Stock, try again later");
//        }
//    }
//
//    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
//        OrderLineItems orderLineItems = new OrderLineItems();
//        orderLineItems.setPrice(orderLineItemsDto.getPrice());
//        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
//        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
//        return orderLineItems;
//    }
//
//}
package com.programmingTech.order_service.service;

import com.programmingTech.order_service.dto.InventoryResponse;
import com.programmingTech.order_service.dto.OrderLineItemsDto;
import com.programmingTech.order_service.dto.OrderRequest;
import com.programmingTech.order_service.model.Order;
import com.programmingTech.order_service.model.OrderLineItems;
import com.programmingTech.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;

    public void placeOrder(OrderRequest orderRequest) {

        // 1️⃣ Map DTO → Entity
        List<OrderLineItems> orderLineItems =
                orderRequest.getOrderLineItemsList()
                        .stream()
                        .map(this::mapToEntity)
                        .toList();

        // 2️⃣ Extract skuCodes
        List<String> skuCodes =
                orderLineItems.stream()
                        .map(OrderLineItems::getSkuCode)
                        .toList();

        // 3️⃣ Call inventory-service (NO transaction here)
        InventoryResponse[] inventoryResponses =
                webClient.build().get()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .scheme("http")
                                        .host("inventory-service")
                                        .path("/api/inventory")
                                        .queryParam("skuCode", skuCodes)
                                        .build()
                        )
                        .retrieve()
                        .bodyToMono(InventoryResponse[].class)
                        .block();

        if (inventoryResponses == null || inventoryResponses.length == 0) {
            throw new IllegalStateException("Inventory service returned no data");
        }

        boolean allInStock =
                Arrays.stream(inventoryResponses)
                        .allMatch(InventoryResponse::getIsInStock);

        if (!allInStock) {
            throw new IllegalStateException("Product is not in stock");
        }

        // 4️⃣ Save order inside transaction
        saveOrder(orderLineItems);
    }

    @Transactional
    void saveOrder(List<OrderLineItems> orderLineItems) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItemsList(orderLineItems);
        orderRepository.save(order);
    }

    private OrderLineItems mapToEntity(OrderLineItemsDto dto) {
        OrderLineItems entity = new OrderLineItems();
        entity.setSkuCode(dto.getSkuCode());
        entity.setPrice(dto.getPrice());
        entity.setQuantity(dto.getQuantity());
        return entity;
    }
}
