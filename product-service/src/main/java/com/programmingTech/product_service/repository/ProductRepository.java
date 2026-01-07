package com.programmingTech.product_service.repository;

import com.programmingTech.product_service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
