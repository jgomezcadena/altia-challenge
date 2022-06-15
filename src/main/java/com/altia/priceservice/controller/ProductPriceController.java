package com.altia.priceservice.controller;

import com.altia.priceservice.model.ProductPrice;
import com.altia.priceservice.service.ProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.altia.priceservice.model.Constants.DATE_TIME_FORMAT;

@RestController
@RequestMapping("product-prices")
public class ProductPriceController {

    private final ProductPriceService service;

    @Autowired
    public ProductPriceController(ProductPriceService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ProductPrice> findProductPrice(
            @RequestParam @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime dateTime,
            @RequestParam String productId,
            @RequestParam String brandId) {

        ProductPrice productPrice = service.findProductPrice(dateTime, productId, brandId);
        return Optional.ofNullable(productPrice)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
