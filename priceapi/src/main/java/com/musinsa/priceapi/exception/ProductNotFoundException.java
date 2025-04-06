package com.musinsa.priceapi.exception;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(String category) {
    super(String.format("No product found for category: %s", category));
  }
}
