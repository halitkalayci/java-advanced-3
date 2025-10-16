package com.turkcell.gatewayserver.filter;

import io.netty.handler.ipfilter.IpSubnetFilterRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 11)
public class IpWhiteListFilter implements GlobalFilter {

    private final Set<String> matchers = new HashSet<>();

    public IpWhiteListFilter(@Value("${gateway.security.allowed-ips:100.108.145.80,127.0.0.1,0:0:0:0:0:0:0:1}") String allowedIps) {
        Arrays.stream(allowedIps.split(","))
                .map(String::trim)
                .filter(ip -> !ip.isEmpty())
                .forEach(ip -> matchers.add(ip));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        InetSocketAddress address = exchange.getRequest().getRemoteAddress();
        String ip = address != null ? address.getAddress().getHostAddress() : "unknown";

        boolean isAllowed = matchers.contains(ip);
        /*if(!isAllowed)
        {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }*/

        return chain.filter(exchange);
    }
}
