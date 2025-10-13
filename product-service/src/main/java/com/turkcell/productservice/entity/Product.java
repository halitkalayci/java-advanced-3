package com.turkcell.productservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="products")
public class Product {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id = UUID.randomUUID();
    private String name;
    private BigDecimal price;
    private int stock;

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal price() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int stock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
