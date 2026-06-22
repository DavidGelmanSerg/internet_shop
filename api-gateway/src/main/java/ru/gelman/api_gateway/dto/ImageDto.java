package ru.gelman.api_gateway.dto;

public record ImageDto(Long id, String originalName, String name, Long size, String contentType, boolean preview) {
}
