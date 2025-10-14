package com.turkcell.orderservice.client;

import com.turkcell.orderservice.contract.GetProductByIdContract;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="product-service", path = "/api/v1/products")
public interface ProductClient {
    @GetMapping("{id}")
    GetProductByIdContract getProductById(@PathVariable UUID id);
}
