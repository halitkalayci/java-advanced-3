package com.turkcell.orderservice.repository;

import com.turkcell.orderservice.entity.OutboxMessage;
import com.turkcell.orderservice.entity.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxMessageRepository extends JpaRepository<OutboxMessage, UUID> {
    List<OutboxMessage> findByStatusOrderByCreatedAtAsc(OutboxStatus status);
}
