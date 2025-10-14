package com.turkcell.productservice.messaging.event;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderCreatedEvent(UUID orderId, BigDecimal totalPrice, List<OrderItem> items) { }

