package com.turkcell.orderservice.controller;

import com.turkcell.orderservice.client.ProductClient;
import com.turkcell.orderservice.client.StaticProductClient;
import com.turkcell.orderservice.contract.GetProductByIdContract;
import com.turkcell.orderservice.dto.CreateOrderRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {
    private final RestTemplate restTemplate;
    private final ProductClient productClient;
    private final StaticProductClient staticProductClient;

    public OrdersController(RestTemplate restTemplate, ProductClient productClient, StaticProductClient staticProductClient) {
        this.restTemplate = restTemplate;
        this.productClient = productClient;
        this.staticProductClient = staticProductClient;
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
            /*String url = "http://product-service/api/v1/products/"+item.productId();
            var response = restTemplate.getForEntity(url, GetProductByIdContract.class)
                    .getBody();
            System.out.println(response.name() + " ürünü için stok okundu: " + response.stock());
            if(response.stock() < item.quantity())
                throw new RuntimeException(response.name() +  " ürünü için stok değeri yetersiz.");*/

            GetProductByIdContract response = productClient.getProductById(item.productId());
            if(response.stock() < item.quantity())
                throw new RuntimeException(response.name() +  " ürünü için stok değeri yetersiz.");
        }

        return "Sipariş alındı";
    }
}
