package ru.gelman.api_gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gelman.api_gateway.dto.OrderDto;
import ru.gelman.api_gateway.dto.UserDto;
import ru.gelman.api_gateway.service.AuthServiceClient;
import ru.gelman.api_gateway.service.OrderServiceClient;
import ru.gelman.api_gateway.service.UserServiceClient;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminPanelController {
    private final UserServiceClient userServiceClient;
    private final OrderServiceClient orderServiceClient;
    private final AuthServiceClient authServiceClient;

    @Autowired
    public AdminPanelController(UserServiceClient userServiceClient, OrderServiceClient orderServiceClient, AuthServiceClient authServiceClient) {
        this.userServiceClient = userServiceClient;
        this.orderServiceClient = orderServiceClient;
        this.authServiceClient = authServiceClient;
    }


    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getFullOrderList(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        if (authServiceClient.verifyToken(authToken)) {
            return orderServiceClient.getOrderList();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=\"User Service Realm\"").build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getFullUserList(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        if (authServiceClient.verifyToken(authToken)) {
            return userServiceClient.getUserList();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=\"User Service Realm\"").build();

    }
}
