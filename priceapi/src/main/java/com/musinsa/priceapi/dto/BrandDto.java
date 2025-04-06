package com.musinsa.priceapi.dto;

public class BrandDto {
  private String name;

  public BrandDto() {}

  public BrandDto(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
