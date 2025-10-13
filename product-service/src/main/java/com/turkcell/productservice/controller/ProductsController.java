package com.turkcell.productservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    @GetMapping("{id}")
    public Product getById(@PathVariable int id)
    {
        return new Product(id, "Ürün 1", "Ürün açıklaması.");
    }

    public record Product(int id, String name, String description) {}
}
