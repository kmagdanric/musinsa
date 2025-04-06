package com.musinsa.priceapi.controller;

import com.musinsa.priceapi.dto.CategoryLowestPriceResponse;
import com.musinsa.priceapi.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PriceController {

  @Autowired private PriceService priceService;

  @GetMapping("/categories/lowest")
  public ResponseEntity<CategoryLowestPriceResponse> getLowestPricePerCategory() {
    try {
      CategoryLowestPriceResponse response = priceService.getLowestPricePerCategory();
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.status(500).build();
    }
  }
}
