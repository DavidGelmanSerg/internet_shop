package ru.gelman.orders_service.dto;

public record KafkaOrderStatusChangedEvent(Long orderId, Long userId, String newStatus) {
}
