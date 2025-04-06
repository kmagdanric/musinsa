package com.musinsa.priceapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CategoryLowestPriceResponse {

  @JsonProperty("details")
  private List<CategoryPriceDTO> details;

  @JsonProperty("총액")
  private String total;

  public CategoryLowestPriceResponse() {}

  public CategoryLowestPriceResponse(List<CategoryPriceDTO> details, String total) {
    this.details = details;
    this.total = total;
  }

  // Getters and setters
  public List<CategoryPriceDTO> getDetails() {
    return details;
  }

  public void setDetails(List<CategoryPriceDTO> details) {
    this.details = details;
  }

  public String getTotal() {
    return total;
  }

  public void setTotal(String total) {
    this.total = total;
  }
}
