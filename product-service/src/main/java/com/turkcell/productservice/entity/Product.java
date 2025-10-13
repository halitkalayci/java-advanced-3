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


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public void setStock(int stock) {
        this.stock = stock;
    }
}
