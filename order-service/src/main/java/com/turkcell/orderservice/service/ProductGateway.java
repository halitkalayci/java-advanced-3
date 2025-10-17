package com.turkcell.orderservice.service;

import com.turkcell.orderservice.client.ProductClient;
import com.turkcell.orderservice.contract.GetProductByIdContract;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductGateway {
    private final ProductClient productClient;

    public ProductGateway(ProductClient productClient) {
        this.productClient = productClient;
    }

    //@CircuitBreaker(name="product-service",fallbackMethod = "fallBack")
    @Retry(name="product-service", fallbackMethod = "fallBack")
    public GetProductByIdContract getProductById(UUID id){
        return productClient.getProductById(id);
    }

    public GetProductByIdContract fallBack(UUID id, Throwable exception) {
        System.out.println("Falling back " + exception.getMessage());
        return new GetProductByIdContract(null, null, null, 0);
    }
}
