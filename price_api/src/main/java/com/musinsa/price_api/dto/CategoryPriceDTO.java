package com.musinsa.price_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryPriceDTO {
    
    @JsonProperty("카테고리")
    private String category;
    
    @JsonProperty("브랜드")
    private String brand;
    
    @JsonProperty("가격")
    private String price;

    public CategoryPriceDTO() {}

    public CategoryPriceDTO(String category, String brand, String price) {
        this.category = category;
        this.brand = brand;
        this.price = price;
    }

    // Getters and setters
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
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
