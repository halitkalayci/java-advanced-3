package com.turkcell.productservice.command.create;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.productservice.entity.EventModel;
import com.turkcell.productservice.entity.Product;
import com.turkcell.productservice.repository.EventModelRepository;
import com.turkcell.productservice.repository.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
@Transactional
public class CreateProductCommandHandler
{
    private final ProductRepository productRepository;
    private final EventModelRepository eventModelRepository;
    private final ObjectMapper objectMapper;

    public CreateProductCommandHandler(ProductRepository productRepository, EventModelRepository eventModelRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.eventModelRepository = eventModelRepository;
        this.objectMapper = objectMapper;
    }

    public UUID handle(CreateProductCommand command) throws JsonProcessingException {
        Product product = new Product();
        product.setName(command.name());
        product.setPrice(command.price());
        product.setStock(command.stock());

        product = productRepository.save(product);

        EventModel eventModel = new EventModel();
        eventModel.setTimestamp(OffsetDateTime.now());
        eventModel.setAggregateId(product.getId());
        eventModel.setEventType(CreateProductCommand.class.getSimpleName());
        String data = objectMapper.writeValueAsString(product);
        eventModel.setEventData(data);
        eventModelRepository.save(eventModel);

        return product.getId();
    }
}
