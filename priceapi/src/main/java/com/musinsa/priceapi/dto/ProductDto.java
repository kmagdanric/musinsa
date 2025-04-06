package com.musinsa.priceapi.dto;

public class ProductDto {
  private String category;
  private int price;
  private Long brandId;

  public ProductDto() {}

  public ProductDto(String category, int price, Long brandId) {
    this.category = category;
    this.price = price;
    this.brandId = brandId;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public Long getBrandId() {
    return brandId;
  }

  public void setBrandId(Long brandId) {
    this.brandId = brandId;
  }
}
