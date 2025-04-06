package com.musinsa.priceapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CategoryPriceRangeResponse {

  @JsonProperty("카테고리")
  private String category;

  @JsonProperty("최저가")
  private List<PriceInfoDto> lowestPrice;

  @JsonProperty("최고가")
  private List<PriceInfoDto> highestPrice;

  public CategoryPriceRangeResponse() {}

  public CategoryPriceRangeResponse(
      String category, List<PriceInfoDto> lowestPrice, List<PriceInfoDto> highestPrice) {
    this.category = category;
    this.lowestPrice = lowestPrice;
    this.highestPrice = highestPrice;
  }

  // Getters and setters...
  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public List<PriceInfoDto> getLowestPrice() {
    return lowestPrice;
  }

  public void setLowestPrice(List<PriceInfoDto> lowestPrice) {
    this.lowestPrice = lowestPrice;
  }

  public List<PriceInfoDto> getHighestPrice() {
    return highestPrice;
  }

  public void setHighestPrice(List<PriceInfoDto> highestPrice) {
    this.highestPrice = highestPrice;
  }
}
