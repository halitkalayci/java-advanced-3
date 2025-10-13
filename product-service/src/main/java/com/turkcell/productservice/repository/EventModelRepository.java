package com.turkcell.productservice.repository;

import com.turkcell.productservice.entity.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventModelRepository extends JpaRepository<EventModel, UUID> {
}
