package com.turkcell.bff.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig
{
    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http)
    {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(
                        ex ->
                                ex
                                        .pathMatchers("/","/public/**","/actuator/**").permitAll()
                                        .anyExchange().authenticated()
                )
                .oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults())
                .logout(l -> l.logoutUrl("/"))
                .build();
    };
}
