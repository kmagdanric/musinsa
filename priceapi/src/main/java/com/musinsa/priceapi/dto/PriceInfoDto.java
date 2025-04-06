package com.musinsa.priceapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceInfoDto {

  @JsonProperty("브랜드")
  private String brand;

  @JsonProperty("가격")
  private String price;

  public PriceInfoDto() {}

  public PriceInfoDto(String brand, String price) {
    this.brand = brand;
    this.price = price;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }
}
