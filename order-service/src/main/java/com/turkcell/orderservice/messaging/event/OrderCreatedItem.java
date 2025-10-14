package com.turkcell.orderservice.messaging.event;

import java.util.UUID;

public record OrderCreatedItem(UUID productId, int quantity) {}
