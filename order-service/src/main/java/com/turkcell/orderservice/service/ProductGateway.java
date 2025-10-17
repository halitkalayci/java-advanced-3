package com.turkcell.orderservice.service;

import com.turkcell.orderservice.client.ProductClient;
import com.turkcell.orderservice.contract.GetProductByIdContract;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductGateway {
    private final ProductClient productClient;

    public ProductGateway(ProductClient productClient) {
        this.productClient = productClient;
    }


    public GetProductByIdContract getProductById(UUID id){
        return productClient.getProductById(id);
    }
}
