package com.turkcell.orderservice.dto;

import java.util.List;
import java.util.UUID;

public class CreateOrderRequest {
    private List<OrderProductItem> items;

    public List<OrderProductItem> getItems() {
        return items;
    }

    public void setItems(List<OrderProductItem> items) {
        this.items = items;
    }

    public record OrderProductItem(UUID productId, Integer quantity) {}
}
