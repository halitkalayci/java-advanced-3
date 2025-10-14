package com.turkcell.orderservice.messaging.consumer;

import com.turkcell.orderservice.messaging.event.StockDecreaseFailedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class StockDecreaseFailedConsumer {
    @Bean
    public Consumer<StockDecreaseFailedEvent> stockDecreaseFailed() {
        return event -> {
            System.out.println("Stok düşülemediği için sipariş iptal ediliyor: Sipariş ID: "+ event.orderId());
        };
    }
}
