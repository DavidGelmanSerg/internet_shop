package ru.gelman.api_gateway.dto;

import java.util.List;

public record CreateOrderRq(Long clientId, List<Long> productIds) {
}
