package ru.gelman.notification_service.events;

public record OrderChangeStatusEvent(Long orderId, Long userId, String newStatus) {
}
