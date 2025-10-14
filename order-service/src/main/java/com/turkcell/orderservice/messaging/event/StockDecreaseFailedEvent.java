package com.turkcell.orderservice.messaging.event;

import java.util.UUID;

public record StockDecreaseFailedEvent(UUID orderId) { }
