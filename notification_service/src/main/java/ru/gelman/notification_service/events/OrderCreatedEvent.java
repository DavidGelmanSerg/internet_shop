package ru.gelman.notification_service.events;

public record OrderCreatedEvent(Long orderId, Long userId) {
}
