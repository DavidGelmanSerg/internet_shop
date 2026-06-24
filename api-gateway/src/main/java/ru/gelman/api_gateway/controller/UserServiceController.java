package ru.gelman.api_gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gelman.api_gateway.service.AuthServiceClient;
import ru.gelman.api_gateway.service.OrderServiceClient;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserServiceController {

    private final AuthServiceClient authServiceClient;
    private final OrderServiceClient orderServiceClient;

    @Autowired
    public UserServiceController(AuthServiceClient authServiceClient, OrderServiceClient orderServiceClient) {
        this.authServiceClient = authServiceClient;
        this.orderServiceClient = orderServiceClient;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register() {
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login() {
        return null;
    }

    @GetMapping("/auth/verify")
    public ResponseEntity<Void> verifyToken() {
        return null;
    }

    @GetMapping("/users/staff")
    public ResponseEntity<Void> getUserAdmins() {
        return null;
    }

    @GetMapping("/users/${id")
    public ResponseEntity<Void> verifyToken(@PathVariable Long id) {
        return null;
    }
}
