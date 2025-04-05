package com.musinsa.price_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.musinsa.price_api.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findFirstByCategoryOrderByPriceAsc(String category);
}
