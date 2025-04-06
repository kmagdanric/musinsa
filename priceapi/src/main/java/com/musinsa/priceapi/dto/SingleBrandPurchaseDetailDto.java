package com.musinsa.priceapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SingleBrandPurchaseDetailDto {

  @JsonProperty("카테고리")
  private String category;

  @JsonProperty("가격")
  private String price;

  public SingleBrandPurchaseDetailDto() {}

  public SingleBrandPurchaseDetailDto(String category, String price) {
    this.category = category;
    this.price = price;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }
}
