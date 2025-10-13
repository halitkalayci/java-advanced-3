package com.turkcell.aggregatorservice.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product-aggregator")
public class ProductAggregatorController {
    private final WebClient.Builder webClientBuilder;

    public ProductAggregatorController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }
    @GetMapping
    public Mono<AggregatedProductDetail> getAggregatedProductDetail(@RequestParam int productId) {
        // http://localhost:8888/api/v1/products/1
        Mono<Product> productMono = webClientBuilder
                .build()
                .get()
                .uri("http://gateway-server/api/v1/products/"+1)
                .retrieve()
                .bodyToMono(Product.class)
                .onErrorResume(throwable -> Mono.error(new RuntimeException("Product service is currently unavailable.")));


        Mono<List<Review>> reviewsMono = webClientBuilder
                .build()
                .get()
                .uri("http://gateway-server/api/v2/reviews?productId="+1)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Review>>() {})
                .onErrorReturn(Collections.emptyList());

        // zip -> iki isteği PARALEL çalıştır, sonuçları birleştir.
        return Mono
                .zip(productMono, reviewsMono)
                .map(tuple -> new AggregatedProductDetail(tuple.getT1(), tuple.getT2()));
    }
    // Cascading Failure -> Bir servisin hatasının tüm sisteme yayılması.


    record Product(int id, String name, String description) {}
    record Review(int id, String author, int rating, String description) {}
    record AggregatedProductDetail(Product product, List<Review> reviews) {}
}
