package ru.gelman.api_gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gelman.api_gateway.service.AuthServiceClient;
import ru.gelman.api_gateway.service.OrderServiceClient;

@RestController
@RequestMapping("/api")
@Slf4j
public class ImageServiceController {
    private final AuthServiceClient authServiceClient;
    private final OrderServiceClient orderServiceClient;

    @Autowired
    public ImageServiceController(AuthServiceClient authServiceClient, OrderServiceClient orderServiceClient) {
        this.authServiceClient = authServiceClient;
        this.orderServiceClient = orderServiceClient;
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        if (authServiceClient.verifyToken(authToken)) {
            return orderServiceClient.getImage(id);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=\"User Service Realm\"").build();

    }
}
