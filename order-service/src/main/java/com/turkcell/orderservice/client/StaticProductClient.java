package com.turkcell.orderservice.client;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "staticFeignClient",
        url = "http://localhost:8888/api/v1/products"
)
public interface StaticProductClient {
    @GetMapping
    Page<ProductDto> getProducts(@RequestParam int pageIndex, @RequestParam int pageSize);


    record ProductDto(UUID id, String name, BigDecimal price, int stock){}
    record Page<T>(List<T> content, boolean last){}
}
