package com.musinsa.priceapi.exception;

public class NoCompleteBrandException extends RuntimeException {
  public NoCompleteBrandException() {
    super("No brand has products for all required categories.");
  }
}
