package com.turkcell.productservice.command.create;

import com.turkcell.productservice.entity.Product;
import com.turkcell.productservice.repository.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Transactional
public class CreateProductCommandHandler
{
    private final ProductRepository productRepository;

    public CreateProductCommandHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public UUID handle(CreateProductCommand command)
    {
        Product product = new Product();
        product.setName(command.name());
        product.setPrice(command.price());
        product.setStock(command.stock());

        product = productRepository.save(product);
        return product.id();
    }
}
