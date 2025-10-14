package com.turkcell.orderservice.contract;

import java.math.BigDecimal;
import java.util.UUID;

public record GetProductByIdContract(UUID id, String name, BigDecimal price, int stock) { }
