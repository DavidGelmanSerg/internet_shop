package ru.gelman.api_gateway.dto;

import ru.gelman.api_gateway.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDto(Long id, LocalDateTime createdAt, OrderStatus status, BigDecimal totalCost, Long clientId) {
}
