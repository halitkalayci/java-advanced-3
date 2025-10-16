package com.turkcell.productservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/value")
@RefreshScope
public class ValueController {
    @Value("${example:NOT_FOUND}")
    private String exampleValue;

    private final Logger logger = LoggerFactory.getLogger(ValueController.class);


    @GetMapping
    public String getValue() {
        logger.info("INFO log örneği");
        logger.warn("WARN log örneği.");
        return exampleValue;
    }
}
