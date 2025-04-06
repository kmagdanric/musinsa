package com.musinsa.priceapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.musinsa.priceapi.model.Brand;
import com.musinsa.priceapi.model.Product;
import com.musinsa.priceapi.repository.BrandRepository;
import com.musinsa.priceapi.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PriceControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ProductRepository productRepository;

  @Autowired private BrandRepository brandRepository;

  @BeforeEach
  public void setUp() {
    productRepository.deleteAll();
    brandRepository.deleteAll();

    // Create test brands
    Brand brandA = brandRepository.save(new Brand("A"));
    Brand brandB = brandRepository.save(new Brand("B"));
    Brand brandC = brandRepository.save(new Brand("C"));

    // Create test products
    productRepository.saveAll(
        List.of(
            new Product("상의", 11200, brandA),
            new Product("상의", 10500, brandB),
            new Product("상의", 10000, brandC),
            new Product("아우터", 5500, brandA),
            new Product("바지", 4200, brandA),
            new Product("스니커즈", 9000, brandA),
            new Product("가방", 2000, brandA),
            new Product("모자", 1700, brandA),
            new Product("양말", 1800, brandA),
            new Product("액세서리", 2300, brandA)));
  }

  @Test
  public void testGetLowestPricePerCategorySuccess() throws Exception {
    mockMvc
        .perform(get("/api/v1/categories/lowest"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.총액").exists())
        .andExpect(jsonPath("$.details").isArray())
        .andExpect(jsonPath("$.details[0].카테고리").isNotEmpty())
        .andExpect(jsonPath("$.details[0].브랜드").isNotEmpty())
        .andExpect(jsonPath("$.details[0].가격").isNotEmpty());
  }

  @Test
  @Transactional
  public void testGetLowestPricePerCategoryMissingData() throws Exception {
    List<Product> socksProducts =
        productRepository.findAll().stream()
            .filter(p -> "양말".equals(p.getCategory()))
            .collect(Collectors.toList());
    productRepository.deleteAll(socksProducts);

    mockMvc.perform(get("/api/v1/categories/lowest")).andExpect(status().is5xxServerError());
  }

  @Test
  public void testGetPriceRangeForCategorySuccess() throws Exception {
    mockMvc
        .perform(get("/api/v1/categories/price-range").param("category", "상의"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.카테고리").value("상의"))
        .andExpect(jsonPath("$.최저가").isArray())
        .andExpect(jsonPath("$.최고가").isArray())
        .andExpect(jsonPath("$.최저가[0].브랜드").value("C"))
        .andExpect(jsonPath("$.최저가[0].가격").value("10,000"))
        .andExpect(jsonPath("$.최고가[0].브랜드").value("A"))
        .andExpect(jsonPath("$.최고가[0].가격").value("11,200"));
  }
}
