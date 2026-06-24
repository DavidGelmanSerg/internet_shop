package ru.gelman.api_gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthServiceClient {
    private final RestTemplate restTemplate;
    @Value("${app.services.users.url}")
    private String userServiceUrl;


    @Autowired
    public AuthServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean verifyToken(String token) {
        return restTemplate.getForEntity(userServiceUrl + "/auth/verify", String.class).getStatusCode().is2xxSuccessful();
    }
}
