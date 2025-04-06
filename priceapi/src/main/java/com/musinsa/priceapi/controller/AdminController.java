package com.musinsa.priceapi.controller;

import com.musinsa.priceapi.dto.BrandDto;
import com.musinsa.priceapi.dto.ProductDto;
import com.musinsa.priceapi.model.Brand;
import com.musinsa.priceapi.model.Product;
import com.musinsa.priceapi.repository.BrandRepository;
import com.musinsa.priceapi.repository.ProductRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

  @Autowired private BrandRepository brandRepository;

  @Autowired private ProductRepository productRepository;

  @PostMapping("/brands")
  public ResponseEntity<Brand> createBrand(@RequestBody BrandDto brandDto) {
    Brand brand = new Brand(brandDto.getName());
    brandRepository.save(brand);
    return ResponseEntity.ok(brand);
  }

  @PutMapping("/brands/{id}")
  public ResponseEntity<Brand> updateBrand(@PathVariable Long id, @RequestBody BrandDto brandDto) {
    Optional<Brand> existing = brandRepository.findById(id);
    if (existing.isPresent()) {
      Brand brand = existing.get();
      brand.setName(brandDto.getName());
      brandRepository.save(brand);
      return ResponseEntity.ok(brand);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/brands/{id}")
  public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
    if (brandRepository.existsById(id)) {
      brandRepository.deleteById(id);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/products")
  public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
    Optional<Brand> brandOpt = brandRepository.findById(productDto.getBrandId());
    if (brandOpt.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    Product product = new Product(productDto.getCategory(), productDto.getPrice(), brandOpt.get());
    productRepository.save(product);
    return ResponseEntity.ok(product);
  }

  @PutMapping("/products/{id}")
  public ResponseEntity<Product> updateProduct(
      @PathVariable Long id, @RequestBody ProductDto productDto) {
    Optional<Product> existing = productRepository.findById(id);
    if (existing.isPresent()) {
      Product product = existing.get();
      product.setCategory(productDto.getCategory());
      product.setPrice(productDto.getPrice());

      if (productDto.getBrandId() != null) {
        Optional<Brand> brandOpt = brandRepository.findById(productDto.getBrandId());
        brandOpt.ifPresent(product::setBrand);
      }
      productRepository.save(product);
      return ResponseEntity.ok(product);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/products/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    if (productRepository.existsById(id)) {
      productRepository.deleteById(id);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }
}
