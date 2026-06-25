package ru.gelman.orders_service.dto;

public record KafkaOrderStatusUpdatedEvent(Long orderId, Long userId, String newStatus) {
}
