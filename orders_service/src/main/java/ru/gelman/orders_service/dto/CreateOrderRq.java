package ru.gelman.orders_service.dto;

import java.util.List;

public record CreateOrderRq(Long clientId, List<Long> productIds) {
}
