package com.altia.priceservice.repository;

import com.altia.priceservice.model.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {

    ProductPrice findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            String productId, String brandId, LocalDateTime date, LocalDateTime date2);

}