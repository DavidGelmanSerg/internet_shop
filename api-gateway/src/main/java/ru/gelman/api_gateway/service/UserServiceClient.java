package ru.gelman.api_gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import ru.gelman.api_gateway.dto.LoginUserResponse;
import ru.gelman.api_gateway.dto.LoginUserRq;
import ru.gelman.api_gateway.dto.RegisterUserRq;

@Component
public class UserServiceClient {
    private final RestTemplate restTemplate;
    private final RestClient client;
    @Value("${app.services.users.url}")
    private String userServiceUrl;

    @Autowired
    public UserServiceClient(RestTemplate restTemplate, RestClient client) {
        this.restTemplate = restTemplate;
        this.client = client;
    }


    public ResponseEntity<LoginUserResponse> registerUser(RegisterUserRq rq) {
        return client
                .post()
                .uri(String.format("%s/register", userServiceUrl))
                .body(rq)
                .retrieve()
                .toEntity(LoginUserResponse.class);
    }

    public ResponseEntity<LoginUserResponse> loginUser(LoginUserRq rq) {
        return client
                .post()
                .uri(String.format("%s/login", userServiceUrl))
                .body(rq)
                .retrieve()
                .toEntity(LoginUserResponse.class);
    }
}
