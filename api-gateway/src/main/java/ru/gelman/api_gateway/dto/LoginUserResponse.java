package ru.gelman.api_gateway.dto;

public record LoginUserResponse(String token, UserDto user) {
}
