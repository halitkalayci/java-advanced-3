package com.turkcell.bff.config;

// Klasik akış -> Girişe yönlendir -> Giriş -> Frontend
            // SessionId->JWT
// Frontend -> BFF -> Gateway -> Servis

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    @LoadBalanced
    WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
    // WebClient.Builder -> builder
    // WebClient.Builder -> loadBalancedWebClientBuilder

    @Bean
    WebClient webClient(
         @Qualifier("loadBalancedWebClientBuilder") WebClient.Builder builder,
         ReactiveClientRegistrationRepository clients,
         ServerOAuth2AuthorizedClientRepository authorizedClients
    ) {
        var oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clients, authorizedClients);
        oauth.setDefaultClientRegistrationId("keycloak");
        oauth.setDefaultOAuth2AuthorizedClient(true);

        return builder.filter(oauth).build();
    }
}
