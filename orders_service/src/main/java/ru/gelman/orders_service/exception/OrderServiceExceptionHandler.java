package ru.gelman.orders_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class OrderServiceExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<String> NotFoundExceptionHandler(NotFoundException e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<String> FailedToSaveImageExceptionHandler(FailedToSaveImageException e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<String> IllegalArgumentExceptionHandler(IllegalArgumentException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
