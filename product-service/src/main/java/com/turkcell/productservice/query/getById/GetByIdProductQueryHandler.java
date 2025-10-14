package com.turkcell.productservice.query.getById;

import com.turkcell.productservice.controller.ProductsController;
import com.turkcell.productservice.entity.Product;
import com.turkcell.productservice.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class GetByIdProductQueryHandler {
    private final ProductRepository productRepository;

    public GetByIdProductQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product handle(GetByIdProductQuery getByIdProductQuery) {
        Product product = productRepository
                .findById(getByIdProductQuery.id())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return product;
    }
}
