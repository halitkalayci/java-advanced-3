package com.turkcell.orderservice.controller;

import com.turkcell.orderservice.client.ProductClient;
import com.turkcell.orderservice.client.StaticProductClient;
import com.turkcell.orderservice.contract.GetProductByIdContract;
import com.turkcell.orderservice.dto.CreateOrderRequest;
import com.turkcell.orderservice.messaging.event.OrderCreatedEvent;
import com.turkcell.orderservice.messaging.event.OrderItem;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {
    private final RestTemplate restTemplate;
    private final ProductClient productClient;
    private final StaticProductClient staticProductClient;
    private final StreamBridge streamBridge;

    public OrdersController(RestTemplate restTemplate, ProductClient productClient, StaticProductClient staticProductClient, StreamBridge streamBridge) {
        this.restTemplate = restTemplate;
        this.productClient = productClient;
        this.staticProductClient = staticProductClient;
        this.streamBridge = streamBridge;
    }

    @GetMapping
    public String getOrders() {
        var response = this.staticProductClient.getProducts(0,20);
        System.out.println("Order servis çalıştı. " + OffsetDateTime.now().toString());
        return "Order Service";
    }

    @PostMapping
    public String addOrder(@RequestBody CreateOrderRequest order) {
        // Sync iletişim.
        for(CreateOrderRequest.OrderProductItem item: order.getItems())
        {
            GetProductByIdContract response = productClient.getProductById(item.productId());
            if(response.stock() < item.quantity())
                throw new RuntimeException(response.name() +  " ürünü için stok değeri yetersiz.");
        }
        OrderCreatedEvent createdEvent = new OrderCreatedEvent(
                UUID.randomUUID(),
                BigDecimal.valueOf(100),
                order.getItems().stream().map(i->new OrderItem(i.productId(), i.quantity())).toList()
        );
        boolean sent = streamBridge.send("orderCreated-out-0", createdEvent);
        return "Sipariş alındı";
    }
}
