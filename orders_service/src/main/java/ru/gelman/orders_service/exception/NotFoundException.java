package ru.gelman.orders_service.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException productNotFound(Long id) {
        return new NotFoundException(String.format("product with id %d not found", id));
    }

    public static NotFoundException orderNotFound(Long id) {
        return new NotFoundException(String.format("order with id %d not found", id));
    }

    public static NotFoundException imageNotFound(Long id) {
        return new NotFoundException(String.format("image with id %d not found", id));
    }

}
