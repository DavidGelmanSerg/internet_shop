package ru.gelman.api_gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
    public OrderDto createOrder(@RequestBody CreateOrderRq rq) {
        return null;
    }

    @GetMapping("/orders/{id}")
    public List<OrderDto> getOrderList(@PathVariable Long id) {
        return null;
    }

    @PatchMapping("/orders/{id}/status")
    public void updateOrderStatus(@PathVariable Long id, @RequestParam("status") String status) {

    }

    @PutMapping("/orders/{id}/products")
    public void addProducts(@PathVariable Long id, @RequestBody List<Long> productIds) {

    }

    @DeleteMapping("/orders/{id}/products")
    public void removeProducts(@PathVariable Long id, @RequestBody List<Long> productIds) {

    }
}
