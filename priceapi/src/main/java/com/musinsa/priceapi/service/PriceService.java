package com.musinsa.priceapi.service;

import com.musinsa.priceapi.dto.CategoryLowestPriceResponse;
import com.musinsa.priceapi.dto.CategoryPriceDto;
import com.musinsa.priceapi.dto.CategoryPriceRangeResponse;
import com.musinsa.priceapi.dto.PriceInfoDto;
import com.musinsa.priceapi.model.Product;
import com.musinsa.priceapi.repository.ProductRepository;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

  @Autowired private ProductRepository productRepository;

  private String formatPrice(int price) {
    NumberFormat formatter = NumberFormat.getNumberInstance(Locale.KOREA);
    return formatter.format(price);
  }

  public CategoryLowestPriceResponse getLowestPricePerCategory() {
    List<String> categories = Arrays.asList("상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리");
    List<CategoryPriceDto> details = new ArrayList<>();
    int total = 0;

    for (String category : categories) {
      Product product =
          productRepository
              .findFirstByCategoryOrderByPriceAsc(category)
              .orElseThrow(
                  () -> new RuntimeException("No product found for category: " + category));
      details.add(
          new CategoryPriceDto(
              category, product.getBrand().getName(), formatPrice(product.getPrice())));
      total += product.getPrice();
    }

    return new CategoryLowestPriceResponse(details, formatPrice(total));
  }

  public CategoryPriceRangeResponse getPriceRangeForCategory(String category) {
    Product lowestProduct =
        productRepository
            .findFirstByCategoryOrderByPriceAsc(category)
            .orElseThrow(() -> new RuntimeException("No product found for category: " + category));
    Product highestProduct =
        productRepository
            .findFirstByCategoryOrderByPriceDesc(category)
            .orElseThrow(() -> new RuntimeException("No product found for category: " + category));

    PriceInfoDto lowestInfo =
        new PriceInfoDto(lowestProduct.getBrand().getName(), formatPrice(lowestProduct.getPrice()));
    PriceInfoDto highestInfo =
        new PriceInfoDto(
            highestProduct.getBrand().getName(), formatPrice(highestProduct.getPrice()));

    return new CategoryPriceRangeResponse(category, List.of(lowestInfo), List.of(highestInfo));
  }
}
