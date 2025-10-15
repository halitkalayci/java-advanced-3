package com.turkcell.bff.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class BFFController {
    @GetMapping("me")
    public Mono<Map<String,Object>> me(@AuthenticationPrincipal OidcUser user)
    {
        return Mono.just(
                Map.of(
                        "sub",user.getSubject(),
                        "username",user.getPreferredUsername(),
                        "email",user.getEmail(),
                        "authorities",user.getAuthorities()
                )
        );
    }
    @GetMapping("hello")
    public Mono<String> hello() {
        return Mono.just("hello");
    }
}
