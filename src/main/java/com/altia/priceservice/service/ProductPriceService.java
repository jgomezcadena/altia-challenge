package com.altia.priceservice.service;

import com.altia.priceservice.model.ProductPrice;
import com.altia.priceservice.repository.ProductPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductPriceService {

    private final ProductPriceRepository repository;

    @Autowired
    public ProductPriceService(ProductPriceRepository repository) {
        this.repository = repository;
    }

    public ProductPrice findProductPrice(LocalDateTime dateTime, String productId, String brandId) {
        return repository.findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                productId, brandId, dateTime, dateTime);

    }
}
