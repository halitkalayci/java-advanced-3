package com.turkcell.orderservice.client;

import com.turkcell.orderservice.client.fallback.ProductClientFallback;
import com.turkcell.orderservice.contract.GetProductByIdContract;
import io.micrometer.observation.annotation.Observed;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="product-service",
        path = "/api/v1/products")
@Observed(name="product-client")
public interface ProductClient {
    @GetMapping("{id}")
    @Observed(name="product-client.getProductById")
    GetProductByIdContract getProductById(@PathVariable UUID id);
}
