package com.musinsa.priceapi.service;

import com.musinsa.priceapi.dto.CategoryLowestPriceResponse;
import com.musinsa.priceapi.dto.CategoryPriceDto;
import com.musinsa.priceapi.dto.CategoryPriceRangeResponse;
import com.musinsa.priceapi.dto.PriceInfoDto;
import com.musinsa.priceapi.dto.SingleBrandPurchaseDetailDto;
import com.musinsa.priceapi.dto.SingleBrandPurchaseResponseDto;
import com.musinsa.priceapi.dto.SingleBrandPurchaseResponseDto.PurchaseInfo;
import com.musinsa.priceapi.exception.NoCompleteBrandException;
import com.musinsa.priceapi.exception.ProductNotFoundException;
import com.musinsa.priceapi.model.Brand;
import com.musinsa.priceapi.model.Product;
import com.musinsa.priceapi.repository.BrandRepository;
import com.musinsa.priceapi.repository.ProductRepository;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

  @Autowired private ProductRepository productRepository;
  @Autowired private BrandRepository brandRepository;

  private static final List<String> CATEGORIES =
      Arrays.asList("상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리");

  private String formatPrice(int price) {
    NumberFormat formatter = NumberFormat.getNumberInstance(Locale.KOREA);
    return formatter.format(price);
  }

  public CategoryLowestPriceResponse getLowestPricePerCategory() {
    List<CategoryPriceDto> details = new ArrayList<>();
    int total = 0;

    for (String category : CATEGORIES) {
      Product product =
          productRepository
              .findFirstByCategoryOrderByPriceAsc(category)
              .orElseThrow(() -> new ProductNotFoundException(category));
      details.add(
          new CategoryPriceDto(
              category, product.getBrand().getName(), formatPrice(product.getPrice())));
      total += product.getPrice();
    }

    return new CategoryLowestPriceResponse(details, formatPrice(total));
  }

  public CategoryPriceRangeResponse getPriceRangeForCategory(String category) {
    Product lowestProduct =
        productRepository
            .findFirstByCategoryOrderByPriceAsc(category)
            .orElseThrow(() -> new ProductNotFoundException(category));
    Product highestProduct =
        productRepository
            .findFirstByCategoryOrderByPriceDesc(category)
            .orElseThrow(() -> new ProductNotFoundException(category));

    PriceInfoDto lowestInfo =
        new PriceInfoDto(lowestProduct.getBrand().getName(), formatPrice(lowestProduct.getPrice()));
    PriceInfoDto highestInfo =
        new PriceInfoDto(
            highestProduct.getBrand().getName(), formatPrice(highestProduct.getPrice()));

    return new CategoryPriceRangeResponse(category, List.of(lowestInfo), List.of(highestInfo));
  }

  public SingleBrandPurchaseResponseDto getLowestSingleBrandPurchase() {
    List<Brand> brands = brandRepository.findAll();
    PurchaseInfo lowestPurchase = null;
    int lowestTotal = Integer.MAX_VALUE;

    for (Brand brand : brands) {
      final MutableContainer container = new MutableContainer();

      List<SingleBrandPurchaseDetailDto> details =
          CATEGORIES.stream()
              .map(
                  category -> {
                    Optional<Product> productOpt =
                        productRepository.findByCategoryAndBrandId(category, brand.getId());
                    if (productOpt.isPresent()) {
                      Product product = productOpt.get();
                      container.total += product.getPrice();
                      return new SingleBrandPurchaseDetailDto(
                          category, formatPrice(product.getPrice()));
                    } else {
                      container.missingCategory = true;
                      return null;
                    }
                  })
              .filter(dto -> dto != null)
              .collect(Collectors.toList());

      if (container.missingCategory || details.size() < CATEGORIES.size()) {
        continue;
      }

      int currentTotal = container.total;
      if (currentTotal < lowestTotal) {
        lowestTotal = currentTotal;
        lowestPurchase = new PurchaseInfo(brand.getName(), details, formatPrice(currentTotal));
      }
    }

    if (lowestPurchase == null) {
      throw new NoCompleteBrandException();
    }
    return new SingleBrandPurchaseResponseDto(lowestPurchase);
  }

  class MutableContainer {
    int total = 0;
    boolean missingCategory = false;
  }
}
