package ru.gelman.orders_service.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductDto(Long id, String name, String description, Integer amount, BigDecimal cost, String type,
                         List<ImageDto> imageInfos) {
}
