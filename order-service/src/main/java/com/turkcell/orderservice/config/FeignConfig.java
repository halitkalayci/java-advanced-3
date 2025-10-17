package com.turkcell.orderservice.config;

import org.springframework.cloud.openfeign.CircuitBreakerNameResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public CircuitBreakerNameResolver circuitBreakerNameResolver() {
        return (clientName, target, method) -> {
            return clientName;
        };
    }
}
