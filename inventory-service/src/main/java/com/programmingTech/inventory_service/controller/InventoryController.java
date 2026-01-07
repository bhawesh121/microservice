//package com.programmingTech.inventory_service.controller;
//
//import com.programmingTech.inventory_service.dto.InventoryResponse;
//import com.programmingTech.inventory_service.repository.InventoryRepository;
//import com.programmingTech.inventory_service.service.InventoryService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/inventory")
//public class InventoryController {
//
//    private final InventoryService inventoryService;
////    http://localhost:8082/api/inventory/iphone13
////    http://localhost:8082/api/inventory?skuCode=iphone13&skuCode=iphone13red
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
//        return inventoryService.isInStock(skuCode);
//    }
//}
//

package com.programmingTech.inventory_service.controller;

import com.programmingTech.inventory_service.dto.InventoryResponse;
import com.programmingTech.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    // http://localhost:8082/api/inventory?skuCode=iphone13&skuCode=iphone14
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(
            @RequestParam("skuCode") List<String> skuCodes) {

        return inventoryService.isInStock(skuCodes);
    }
}
