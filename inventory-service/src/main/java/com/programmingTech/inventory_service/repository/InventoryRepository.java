package com.programmingTech.inventory_service.repository;

import com.programmingTech.inventory_service.dto.InventoryResponse;
import com.programmingTech.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    List<Inventory> findBySkuCodeIn(Collection<String> skuCodes);
}
