package com.turkcell.aggregatorservice.config;

import io.netty.channel.ChannelOption;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;


@Configuration
public class WebClientConfig
{

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        HttpClient httpClient = HttpClient
                .create()
                // Bağlantı kurmak için max. süre
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                // Bağlantı kuruldu, cevap bekleniyor..
                .responseTimeout(Duration.ofSeconds(10));

        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient));
    }
}
