//package com.programmingTech.inventory_service.service;
//
//import com.programmingTech.inventory_service.dto.InventoryResponse;
//import com.programmingTech.inventory_service.repository.InventoryRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//public class InventoryService {
//
//    private final InventoryRepository inventoryRepository;
//
//    @Transactional(readOnly = true)
//    public List<InventoryResponse> isInStock(List<String> skuCude) {
//        return inventoryRepository.findBySkuCodeIn(skuCude)
//                .stream().map(inventory -> InventoryResponse.builder()
//                        .skuCode(inventory.getSkuCode())
//                        .isInStock(inventory.getQuantity()>0)
//                        .build()).toList();
//    }
//}
package com.programmingTech.inventory_service.service;

import com.programmingTech.inventory_service.dto.InventoryResponse;
import com.programmingTech.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes) {

        List<InventoryResponse> list = inventoryRepository.findBySkuCodeIn(skuCodes)
                .stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                )
                .toList();

        return list ;
    }
}
