package ru.gelman.orders_service.dto;

import java.math.BigDecimal;

public record CreateProductRq(String name, String description, Integer amount, BigDecimal cost, String type) {
}
