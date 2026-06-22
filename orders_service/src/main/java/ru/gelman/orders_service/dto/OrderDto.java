package ru.gelman.orders_service.dto;

import ru.gelman.orders_service.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDto(Long id, LocalDateTime createdAt, OrderStatus status, BigDecimal totalCost, Long clientId) {
}
