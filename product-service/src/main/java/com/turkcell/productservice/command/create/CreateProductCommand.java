package com.turkcell.productservice.command.create;

import java.math.BigDecimal;

public record CreateProductCommand(String name, BigDecimal price, int stock) { }
