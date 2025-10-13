package com.turkcell.productservice.controller;

import com.turkcell.productservice.command.create.CreateProductCommand;
import com.turkcell.productservice.command.create.CreateProductCommandHandler;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    private final CreateProductCommandHandler createProductCommandHandler;

    public ProductsController(CreateProductCommandHandler createProductCommandHandler) {
        this.createProductCommandHandler = createProductCommandHandler;
    }

    @GetMapping("{id}")
    public Product getById(@PathVariable int id)
    {
        return new Product(id, "Ürün 1", "Ürün açıklaması.");
    }

    @PostMapping
    public UUID create(@RequestBody CreateProductCommand command)
    {
        return createProductCommandHandler.handle(command);
    }

    public record Product(int id, String name, String description) {}
}
