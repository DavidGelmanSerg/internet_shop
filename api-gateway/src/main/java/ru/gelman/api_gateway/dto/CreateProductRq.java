package ru.gelman.api_gateway.dto;

import java.math.BigDecimal;

public record CreateProductRq(String name, String description, Integer amount, BigDecimal cost, String type) {
}
