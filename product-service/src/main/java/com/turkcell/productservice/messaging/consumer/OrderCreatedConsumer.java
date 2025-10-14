package com.turkcell.productservice.messaging.consumer;

import com.turkcell.productservice.messaging.event.OrderCreatedEvent;
import com.turkcell.productservice.messaging.event.StockDecreaseFailedEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
public class OrderCreatedConsumer {
    private final StreamBridge streamBridge;

    public OrderCreatedConsumer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Bean("processOrder")
    public Consumer<OrderCreatedEvent> orderCreatedEventConsumer()
    {
        return event -> {
            System.out.println("Event yakalandı.");

            System.out.println("Stok düşürülüyor..");
            StockDecreaseFailedEvent failedEvent = new StockDecreaseFailedEvent(event.orderId());
            Message<StockDecreaseFailedEvent> msg = MessageBuilder.withPayload(failedEvent).build();

            boolean sent = streamBridge.send("products", msg);

            System.out.println("Order Created Event Yakalandı: " + event.orderId());
        };
    }
}
