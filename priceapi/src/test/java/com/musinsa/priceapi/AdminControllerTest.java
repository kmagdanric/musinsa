package com.musinsa.priceapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.priceapi.dto.BrandDto;
import com.musinsa.priceapi.dto.ProductDto;
import com.musinsa.priceapi.model.Brand;
import com.musinsa.priceapi.model.Product;
import com.musinsa.priceapi.repository.BrandRepository;
import com.musinsa.priceapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AdminControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private BrandRepository brandRepository;

  @Autowired private ProductRepository productRepository;

  @Autowired private ObjectMapper objectMapper;

  private Brand testBrand;

  @BeforeEach
  public void setUp() {
    productRepository.deleteAll();
    brandRepository.deleteAll();

    // Create a test brand for product tests
    testBrand = brandRepository.save(new Brand("TestBrand"));
  }

  @Test
  public void testCreateBrand() throws Exception {
    BrandDto brandDto = new BrandDto("TestBrand");
    mockMvc
        .perform(
            post("/api/v1/admin/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brandDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("TestBrand"));
  }

  @Test
  public void testUpdateBrand() throws Exception {
    Brand brand = new Brand("OriginalBrand");
    brand = brandRepository.save(brand);

    BrandDto updatedDto = new BrandDto("UpdatedBrand");

    mockMvc
        .perform(
            put("/api/v1/admin/brands/" + brand.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("UpdatedBrand"));
  }

  @Test
  public void testDeleteBrand() throws Exception {
    Brand brand = new Brand("DeleteBrand");
    brand = brandRepository.save(brand);

    mockMvc.perform(delete("/api/v1/admin/brands/" + brand.getId())).andExpect(status().isOk());
  }

  @Test
  public void testCreateProduct() throws Exception {
    ProductDto productDto = new ProductDto("상의", 10000, testBrand.getId());

    mockMvc
        .perform(
            post("/api/v1/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.category").value("상의"))
        .andExpect(jsonPath("$.price").value(10000))
        .andExpect(jsonPath("$.brand.id").value(testBrand.getId()));
  }

  @Test
  public void testCreateProductWithInvalidBrand() throws Exception {
    ProductDto productDto = new ProductDto("상의", 10000, 999L); // Non-existent brand ID

    mockMvc
        .perform(
            post("/api/v1/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testUpdateProduct() throws Exception {
    Product product = new Product("상의", 10000, testBrand);
    product = productRepository.save(product);
    ProductDto updateDto = new ProductDto("바지", 20000, testBrand.getId());

    mockMvc
        .perform(
            put("/api/v1/admin/products/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.category").value("바지"))
        .andExpect(jsonPath("$.price").value(20000))
        .andExpect(jsonPath("$.brand.id").value(testBrand.getId()));
  }

  @Test
  public void testUpdateProductWithInvalidBrand() throws Exception {
    Product product = new Product("상의", 10000, testBrand);
    product = productRepository.save(product);
    ProductDto updateDto = new ProductDto("바지", 20000, 999L);

    mockMvc
        .perform(
            put("/api/v1/admin/products/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.category").value("바지"))
        .andExpect(jsonPath("$.price").value(20000))
        .andExpect(
            jsonPath("$.brand.id").value(testBrand.getId())); // Brand should remain unchanged
  }

  @Test
  public void testDeleteProduct() throws Exception {
    Product product = new Product("상의", 10000, testBrand);
    product = productRepository.save(product);

    mockMvc.perform(delete("/api/v1/admin/products/" + product.getId())).andExpect(status().isOk());
  }

  @Test
  public void testDeleteNonExistentProduct() throws Exception {
    mockMvc.perform(delete("/api/v1/admin/products/999")).andExpect(status().isNotFound());
  }
}
