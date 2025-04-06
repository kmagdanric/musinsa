package com.musinsa.priceapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SingleBrandPurchaseResponseDto {

  @JsonProperty("최저가")
  private PurchaseInfo lowestPurchase;

  public SingleBrandPurchaseResponseDto() {}

  public SingleBrandPurchaseResponseDto(PurchaseInfo lowestPurchase) {
    this.lowestPurchase = lowestPurchase;
  }

  public PurchaseInfo getLowestPurchase() {
    return lowestPurchase;
  }

  public void setLowestPurchase(PurchaseInfo lowestPurchase) {
    this.lowestPurchase = lowestPurchase;
  }

  public static class PurchaseInfo {
    @JsonProperty("브랜드")
    private String brand;

    @JsonProperty("카테고리")
    private List<SingleBrandPurchaseDetailDto> details;

    @JsonProperty("총액")
    private String total;

    public PurchaseInfo() {}

    public PurchaseInfo(String brand, List<SingleBrandPurchaseDetailDto> details, String total) {
      this.brand = brand;
      this.details = details;
      this.total = total;
    }

    // Getters and setters
    public String getBrand() {
      return brand;
    }

    public void setBrand(String brand) {
      this.brand = brand;
    }

    public List<SingleBrandPurchaseDetailDto> getDetails() {
      return details;
    }

    public void setDetails(List<SingleBrandPurchaseDetailDto> details) {
      this.details = details;
    }

    public String getTotal() {
      return total;
    }

    public void setTotal(String total) {
      this.total = total;
    }
  }
}
