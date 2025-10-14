package com.turkcell.productservice.messaging.event;

import java.util.UUID;

public record OrderItem(UUID productId, int quantity) {}
