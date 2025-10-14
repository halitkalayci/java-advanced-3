package com.turkcell.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.orderservice.client.ProductClient;
import com.turkcell.orderservice.client.StaticProductClient;
import com.turkcell.orderservice.contract.GetProductByIdContract;
import com.turkcell.orderservice.dto.CreateOrderRequest;
import com.turkcell.orderservice.entity.OutboxMessage;
import com.turkcell.orderservice.messaging.event.OrderCreatedEvent;
import com.turkcell.orderservice.messaging.event.OrderCreatedItem;
import com.turkcell.orderservice.repository.OutboxMessageRepository;
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
    private final OutboxMessageRepository outboxMessageRepository;
    private final ObjectMapper objectMapper;

    public OrdersController(RestTemplate restTemplate, ProductClient productClient, StaticProductClient staticProductClient, StreamBridge streamBridge, OutboxMessageRepository outboxMessageRepository, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.productClient = productClient;
        this.staticProductClient = staticProductClient;
        this.outboxMessageRepository = outboxMessageRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public String getOrders() {
        var response = this.staticProductClient.getProducts(0,20);
        System.out.println("Order servis çalıştı. " + OffsetDateTime.now().toString());
        return "Order Service";
    }

    @PostMapping
    public String addOrder(@RequestBody CreateOrderRequest order) throws JsonProcessingException {
        // Sync iletişim.
        for(CreateOrderRequest.OrderProductItem item: order.getItems())
        {
            GetProductByIdContract response = productClient.getProductById(item.productId());
            if(response.stock() < item.quantity())
                throw new RuntimeException(response.name() +  " ürünü için stok değeri yetersiz.");
        }
        // Bu işi controller yapmamalı.
        // Outbox isimli bir tabloya, bu eventin gönderilmesi gerektiğini söylemeli.
        OrderCreatedEvent createdEvent = new OrderCreatedEvent(
                UUID.randomUUID(),
                BigDecimal.valueOf(100),
                order.getItems().stream().map(i->new OrderCreatedItem(i.productId(), i.quantity())).toList()
        );

        OutboxMessage outboxMessage = new OutboxMessage();
        outboxMessage.setAggregateType("Order");
        outboxMessage.setEventType("OrderCreatedEvent");
        outboxMessage.setAggregateId(UUID.randomUUID()); // order'in idsi
        outboxMessage.setPayloadJson(objectMapper.writeValueAsString(createdEvent));

        outboxMessageRepository.save(outboxMessage);
        return "Sipariş alındı";
    }
}
