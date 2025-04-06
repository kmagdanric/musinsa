package com.musinsa.priceapi.controller;

import com.musinsa.priceapi.dto.CategoryLowestPriceResponse;
import com.musinsa.priceapi.dto.CategoryPriceRangeResponse;
import com.musinsa.priceapi.dto.SingleBrandPurchaseResponseDto;
import com.musinsa.priceapi.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PriceController {

  @Autowired private PriceService priceService;

  @GetMapping("/categories/lowest")
  public ResponseEntity<CategoryLowestPriceResponse> getLowestPricePerCategory() {
    CategoryLowestPriceResponse response = priceService.getLowestPricePerCategory();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/categories/price-range")
  public ResponseEntity<CategoryPriceRangeResponse> getPriceRangeForCategory(
      @RequestParam String category) {
    CategoryPriceRangeResponse response = priceService.getPriceRangeForCategory(category);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/brands/single-purchase")
  public ResponseEntity<SingleBrandPurchaseResponseDto> getLowestSingleBrandPurchase() {
    SingleBrandPurchaseResponseDto response = priceService.getLowestSingleBrandPurchase();
    return ResponseEntity.ok(response);
  }
}
