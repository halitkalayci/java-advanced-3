package com.turkcell.orderservice.client.fallback;

import com.turkcell.orderservice.client.ProductClient;
import com.turkcell.orderservice.contract.GetProductByIdContract;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.logging.Logger;

@Component
public class ProductClientFallback implements ProductClient {
    private static final Logger logger = Logger.getLogger(ProductClientFallback.class.getName());

    @Override
    public GetProductByIdContract getProductById(UUID id) {
        logger.warning("ProductClientFallback tetiklendi: getProductById");
        throw new RuntimeException("Product service erişim dışı.");
    }
}
