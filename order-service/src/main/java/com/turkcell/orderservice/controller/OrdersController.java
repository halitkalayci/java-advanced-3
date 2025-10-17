package com.turkcell.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.orderservice.client.ProductClient;
import com.turkcell.orderservice.client.StaticProductClient;
import com.turkcell.orderservice.contract.GetProductByIdContract;
import com.turkcell.orderservice.dto.CreateOrderRequest;
import com.turkcell.orderservice.entity.Order;
import com.turkcell.orderservice.entity.OrderItem;
import com.turkcell.orderservice.entity.OutboxMessage;
import com.turkcell.orderservice.messaging.event.OrderCreatedEvent;
import com.turkcell.orderservice.messaging.event.OrderCreatedItem;
import com.turkcell.orderservice.repository.OrderItemRepository;
import com.turkcell.orderservice.repository.OrderRepository;
import com.turkcell.orderservice.repository.OutboxMessageRepository;
import io.micrometer.observation.annotation.Observed;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@Observed(name="orders-controller")
public class OrdersController {
    private final RestTemplate restTemplate;
    private final ProductClient productClient;
    private final StaticProductClient staticProductClient;
    private final OutboxMessageRepository outboxMessageRepository;
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrdersController(RestTemplate restTemplate, ProductClient productClient, StaticProductClient staticProductClient, OutboxMessageRepository outboxMessageRepository, ObjectMapper objectMapper, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.restTemplate = restTemplate;
        this.productClient = productClient;
        this.staticProductClient = staticProductClient;
        this.outboxMessageRepository = outboxMessageRepository;
        this.objectMapper = objectMapper;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @GetMapping
    public String getOrders() {
        var response = this.staticProductClient.getProducts(0,20);
        System.out.println("Order servis çalıştı. " + OffsetDateTime.now().toString());
        return "Order Service";
    }

    @PostMapping
    @Observed(name="orders-controller.addOrder")
    public String addOrder(@RequestBody CreateOrderRequest createOrderRequest) throws JsonProcessingException {
        Order order = new Order();
        order.setCustomerId(UUID.randomUUID());
        orderRepository.save(order);

        for(CreateOrderRequest.OrderProductItem item: createOrderRequest.getItems())
        {
            GetProductByIdContract response = productClient.getProductById(item.productId());
            if(response.stock() < item.quantity())
                throw new RuntimeException(response.name() +  " ürünü için stok değeri yetersiz.");
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(item.quantity());
            orderItem.setProductId(response.id());
            orderItem.setUnitPrice(response.price());
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
        }
        // Bu işi controller yapmamalı.
        // Outbox isimli bir tabloya, bu eventin gönderilmesi gerektiğini söylemeli.
        OrderCreatedEvent createdEvent = new OrderCreatedEvent(
                order.id(),
                BigDecimal.valueOf(100),
                createOrderRequest.getItems().stream().map(i->new OrderCreatedItem(i.productId(), i.quantity())).toList()
        );

        OutboxMessage outboxMessage = new OutboxMessage();
        outboxMessage.setAggregateType("Order");
        outboxMessage.setEventType("OrderCreatedEvent");
        outboxMessage.setAggregateId(order.id()); // order'in idsi
        outboxMessage.setPayloadJson(objectMapper.writeValueAsString(createdEvent));

        outboxMessageRepository.save(outboxMessage);
        return "Sipariş alındı";
    }
}
