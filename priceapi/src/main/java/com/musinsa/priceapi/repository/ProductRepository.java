package com.musinsa.priceapi.repository;

import com.musinsa.priceapi.model.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Optional<Product> findFirstByCategoryOrderByPriceAsc(String category);

  Optional<Product> findFirstByCategoryOrderByPriceDesc(String category);

  Optional<Product> findByCategoryAndBrandId(String category, Long brandId);
}
