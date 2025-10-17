package com.turkcell.orderservice.service;

import com.turkcell.orderservice.client.ProductClient;
import com.turkcell.orderservice.contract.GetProductByIdContract;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductGateway {
    private final ProductClient productClient;

    public ProductGateway(ProductClient productClient) {
        this.productClient = productClient;
    }

    @CircuitBreaker(name="product-service",fallbackMethod = "fallBack")
    @Retry(name="product-service", fallbackMethod = "fallBack")
    @TimeLimiter(name="product-service", fallbackMethod = "fallBack")
    public CompletableFuture<GetProductByIdContract> getProductById(UUID id){
        return CompletableFuture.supplyAsync(() -> productClient.getProductById(id));
    }

    public CompletableFuture<GetProductByIdContract> fallBack(UUID id, Throwable exception) {
        System.out.println("Falling back " + exception.getMessage());
        return CompletableFuture.completedFuture(new GetProductByIdContract(null, null, null, 0));
    }
}
