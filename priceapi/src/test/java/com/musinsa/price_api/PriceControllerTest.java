package com.musinsa.priceapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.musinsa.priceapi.model.Product;
import com.musinsa.priceapi.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ProductRepository productRepository;

  @Test
  public void testGetLowestPricePerCategory_success() throws Exception {
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
    // Remove all products in the "양말" (socks) category
    List<Product> socksProducts =
        productRepository.findAll().stream()
            .filter(p -> "양말".equals(p.getCategory()))
            .collect(Collectors.toList());
    productRepository.deleteAll(socksProducts);

    // When the service cannot find a product for one category, it should throw an exception
    mockMvc.perform(get("/api/v1/categories/lowest")).andExpect(status().is5xxServerError());
  }

  @Test
  public void testGetPriceRangeForCategory_success() throws Exception {
    mockMvc
        .perform(get("/api/v1/categories/price-range").param("category", "상의"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.카테고리").value("상의"))
        .andExpect(jsonPath("$.최저가").isArray())
        .andExpect(jsonPath("$.최고가").isArray())
        .andExpect(jsonPath("$.최저가[0].브랜드").isNotEmpty())
        .andExpect(jsonPath("$.최저가[0].가격").isNotEmpty())
        .andExpect(jsonPath("$.최고가[0].브랜드").isNotEmpty())
        .andExpect(jsonPath("$.최고가[0].가격").isNotEmpty());
  }
}
