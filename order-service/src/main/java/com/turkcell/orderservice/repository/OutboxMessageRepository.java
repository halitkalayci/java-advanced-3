package com.turkcell.orderservice.repository;

import com.turkcell.orderservice.entity.OutboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutboxMessageRepository extends JpaRepository<OutboxMessage, UUID> {
}
