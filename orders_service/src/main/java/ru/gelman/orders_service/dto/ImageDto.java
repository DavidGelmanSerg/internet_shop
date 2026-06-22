package ru.gelman.orders_service.dto;

public record ImageDto(Long id, String originalName, String name, Long size, String contentType, boolean preview) {
}
