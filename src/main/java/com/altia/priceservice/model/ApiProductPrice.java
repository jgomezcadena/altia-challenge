package com.altia.priceservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Currency;

import static com.altia.priceservice.model.Constants.DATE_TIME_FORMAT;

public class ApiProductPrice {

    private String brandId;
    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime startDate;
    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime endDate;
    private String tariffId;
    private String productId;
    private Float price;
    private Currency currency;

    public ApiProductPrice() {
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getTariffId() {
        return tariffId;
    }

    public void setTariffId(String tariffId) {
        this.tariffId = tariffId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
