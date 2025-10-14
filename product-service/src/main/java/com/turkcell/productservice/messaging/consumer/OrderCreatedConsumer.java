package com.turkcell.productservice.messaging.consumer;

import com.turkcell.productservice.messaging.event.OrderCreatedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class OrderCreatedConsumer {
    @Bean("processOrder")
    public Consumer<OrderCreatedEvent> orderCreatedEventConsumer()
    {
        return event -> {
            System.out.println("Event yakalandı.");
            int a = 1/0;
            System.out.println("Event handle edildi.");
            System.out.println("Order Created Event Yakalandı: " + event.orderId());
        };
    }
}
