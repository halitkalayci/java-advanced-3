package com.turkcell.productservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/value")
public class ValueController {
    @Value("${example:NOT_FOUND}")
    private String exampleValue;

    @GetMapping
    public String getValue() {
        return exampleValue;
    }
}
