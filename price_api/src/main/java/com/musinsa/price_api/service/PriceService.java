package com.musinsa.price_api.service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.musinsa.price_api.dto.CategoryLowestPriceResponse;
import com.musinsa.price_api.dto.CategoryPriceDTO;
import com.musinsa.price_api.model.Product;
import com.musinsa.price_api.repository.ProductRepository;

@Service
public class PriceService {

    @Autowired
    private ProductRepository productRepository;
    
    // Helper to format price as currency (or you can customize as needed)
    private String formatPrice(int price) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.KOREA);
        return formatter.format(price);
    }

    public CategoryLowestPriceResponse getLowestPricePerCategory() {
        // Define the list of categories (as specified in the assignment)
        List<String> categories = Arrays.asList("상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리");
        List<CategoryPriceDTO> details = new ArrayList<>();
        int total = 0;
        
        for (String category : categories) {
            Product product = productRepository.findFirstByCategoryOrderByPriceAsc(category)
                    .orElseThrow(() -> new RuntimeException("No product found for category: " + category));
            details.add(new CategoryPriceDTO(category, product.getBrand().getName(), formatPrice(product.getPrice())));
            total += product.getPrice();
        }
        
        // Create the response with the formatted total
        return new CategoryLowestPriceResponse(details, formatPrice(total));
    }
}
