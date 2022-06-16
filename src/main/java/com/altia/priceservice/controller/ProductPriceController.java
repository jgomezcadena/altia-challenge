package com.altia.priceservice.controller;

import com.altia.priceservice.model.ApiProductPrice;
import com.altia.priceservice.model.ProductPrice;
import com.altia.priceservice.service.ProductPriceService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ObjectMapper objectMapper;

    @Autowired
    public ProductPriceController(ProductPriceService service) {
        this.service = service;
        objectMapper = buildObjectMapper();
    }

    @GetMapping
    public ResponseEntity<ApiProductPrice> findProductPrice(
            @RequestParam @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime dateTime,
            @RequestParam String productId,
            @RequestParam String brandId) {

        ProductPrice productPrice = service.findProductPrice(dateTime, productId, brandId);
        ApiProductPrice apiProductPrice = objectMapper.convertValue(productPrice, ApiProductPrice.class);

        return Optional.ofNullable(apiProductPrice)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    private ObjectMapper buildObjectMapper() {
        final ObjectMapper objectMapper;
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
