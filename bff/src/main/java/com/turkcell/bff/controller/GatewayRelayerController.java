package com.turkcell.bff.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Set;

@RequestMapping("/api")
@RestController
public class GatewayRelayerController {
    private final WebClient webClient;

    public GatewayRelayerController(WebClient webClient) {
        this.webClient = webClient;
    }


    @RequestMapping("/**")
    public Mono<ResponseEntity<byte[]>> relay(ServerWebExchange exchange,
                                              @RequestBody(required=false) Mono<byte[]> body) {
        // Method kontrolü..
        URI fullPath = exchange.getRequest().getURI();
        String downstreamPath = exchange.getRequest().getURI().getPath();
        String query = exchange.getRequest().getURI().getRawQuery();
        // id=1 username=halit
        // id=1&username=halit

        String pathWithQuery = query != null ? downstreamPath + "?" + query : downstreamPath;
        var headers = exchange.getRequest().getHeaders();

        String fullRequestPath = "http://gateway-server/" + pathWithQuery;

        return webClient
                .method(exchange.getRequest().getMethod())
                .uri(fullRequestPath)
                .body(body != null ? BodyInserters.fromPublisher(body, byte[].class) : BodyInserters.empty())
                .exchangeToMono(clientResponse -> clientResponse.toEntity(byte[].class));
    }

}
