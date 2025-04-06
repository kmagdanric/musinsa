package com.musinsa.priceapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CategoryLowestPriceResponse {

  @JsonProperty("details")
  private List<CategoryPriceDto> details;

  @JsonProperty("총액")
  private String total;

  public CategoryLowestPriceResponse() {}

  public CategoryLowestPriceResponse(List<CategoryPriceDto> details, String total) {
    this.details = details;
    this.total = total;
  }

  // Getters and setters
  public List<CategoryPriceDto> getDetails() {
    return details;
  }

  public void setDetails(List<CategoryPriceDto> details) {
    this.details = details;
  }

  public String getTotal() {
    return total;
  }

  public void setTotal(String total) {
    this.total = total;
  }
}
