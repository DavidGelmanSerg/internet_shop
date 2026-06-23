package ru.gelman.api_gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gelman.api_gateway.dto.CreateOrderRq;
import ru.gelman.api_gateway.dto.OrderDto;
import ru.gelman.api_gateway.service.AuthServiceClient;
import ru.gelman.api_gateway.service.OrderServiceClient;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class OrderServiceController {

    private final AuthServiceClient authServiceClient;
    private final OrderServiceClient orderServiceClient;

    @Autowired
    public OrderServiceController(AuthServiceClient authServiceClient, OrderServiceClient orderServiceClient) {
        this.authServiceClient = authServiceClient;
        this.orderServiceClient = orderServiceClient;
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRq rq, @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        if (authServiceClient.verifyToken(authToken)) {
            return orderServiceClient.createOrder(rq);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=\"User Service Realm\"").build();
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<List<OrderDto>> getOrderList(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        if (authServiceClient.verifyToken(authToken)) {
            return orderServiceClient.getOrderListForClient(id);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=\"User Service Realm\"").build();
    }

    @PatchMapping("/orders/{id}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long id, @RequestParam("status") String status, @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        if (authServiceClient.verifyToken(authToken)) {
            return orderServiceClient.updateOrderStatus(id, status);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=\"User Service Realm\"").build();

    }

    @PutMapping("/orders/{id}/products")
    public ResponseEntity<Void> addProducts(@PathVariable Long id, @RequestBody List<Long> productIds, @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        if (authServiceClient.verifyToken(authToken)) {
            return orderServiceClient.addProducts(id, productIds);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=\"User Service Realm\"").build();
    }

    @DeleteMapping("/orders/{id}/products")
    public ResponseEntity<Void> removeProducts(@PathVariable Long id, @RequestBody List<Long> productIds, @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        if (authServiceClient.verifyToken(authToken)) {
            return orderServiceClient.removeProducts(id, productIds);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=\"User Service Realm\"").build();
    }
}
