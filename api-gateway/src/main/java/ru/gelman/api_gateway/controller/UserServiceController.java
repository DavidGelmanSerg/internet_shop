package ru.gelman.api_gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gelman.api_gateway.dto.LoginUserResponse;
import ru.gelman.api_gateway.dto.LoginUserRq;
import ru.gelman.api_gateway.dto.RegisterUserRq;
import ru.gelman.api_gateway.service.UserServiceClient;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserServiceController {
    private final UserServiceClient userServiceClient;

    @Autowired
    public UserServiceController(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterUserRq rq) {
        return userServiceClient.registerUser(rq);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginUserRq rq) {
        return userServiceClient.loginUser(rq);
    }

    @GetMapping("/personal/{id}")
    public ResponseEntity<LoginUserResponse> getUserInfo(@PathVariable Long id) {
        return userServiceClient.getUserInfo(id);
    }
}
