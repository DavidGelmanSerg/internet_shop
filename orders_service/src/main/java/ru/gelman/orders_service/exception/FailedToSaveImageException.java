package ru.gelman.orders_service.exception;

public class FailedToSaveImageException extends RuntimeException {
    public FailedToSaveImageException(String message) {
        super(message);
    }
}
