package com.turkcell.orderservice.repository;

import com.turkcell.orderservice.entity.Order;
import com.turkcell.orderservice.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}

