package com.turkcell.orderservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {
    @GetMapping
    public String getOrders() {
        System.out.println("Order servis çalıştı. " + OffsetDateTime.now().toString());
        return "Order Service";
    }
}
