package ru.gelman.api_gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gelman.api_gateway.dto.LoginUserResponse;
import ru.gelman.api_gateway.dto.LoginUserRq;
import ru.gelman.api_gateway.dto.RegisterUserRq;
import ru.gelman.api_gateway.service.AuthServiceClient;
import ru.gelman.api_gateway.service.UserServiceClient;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserServiceController {

    private final AuthServiceClient authServiceClient;
    private final UserServiceClient userServiceClient;

    @Autowired
    public UserServiceController(AuthServiceClient authServiceClient, UserServiceClient userServiceClient) {
        this.authServiceClient = authServiceClient;
        this.userServiceClient = userServiceClient;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginUserResponse> register(@RequestBody RegisterUserRq rq) {
        return userServiceClient.registerUser(rq);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> login(@RequestBody LoginUserRq rq) {
        return userServiceClient.loginUser(rq);
    }

}
