package ru.gelman.notification_service.events;

public record UserRegisterEvent(Long userId, String email) {
}
