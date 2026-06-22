package ru.gelman.orders_service.dto;

public record KafkaOrderCreatedEvent(Long orderId, Long userId) {
}
