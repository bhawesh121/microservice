//package com.programmingTech.order_service.controller;
//
//import com.programmingTech.order_service.dto.OrderLineItemsDto;
//import com.programmingTech.order_service.dto.OrderRequest;
//import com.programmingTech.order_service.dto.OrderResponse;
//import com.programmingTech.order_service.service.OrderService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/order")
//@RequiredArgsConstructor
//public class OrderController {
//
//    private final OrderService orderService;
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public String placeOrder(@RequestBody OrderRequest order) throws IllegalAccessException {
//        orderService.placeOrder(order);
//        return "Order Placed Successfully";
//    }
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<OrderResponse> getOrderLineItems(@RequestParam String orderNumber) {
//        return orderService.getAllOrders();
//    }
//}
package com.programmingTech.order_service.controller;

import com.programmingTech.order_service.dto.OrderRequest;
import com.programmingTech.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(
            @RequestBody @Valid OrderRequest orderRequest) throws IllegalAccessException {

        orderService.placeOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Order placed successfully");
    }
}

