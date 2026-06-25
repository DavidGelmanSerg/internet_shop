package ru.gelman.api_gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.gelman.api_gateway.dto.LoginUserResponse;
import ru.gelman.api_gateway.dto.LoginUserRq;
import ru.gelman.api_gateway.dto.RegisterUserRq;
import ru.gelman.api_gateway.dto.UserDto;

import java.util.List;

@Component
public class UserServiceClient {
    private final RestClient restClient;
    @Value("${app.services.users.url}")
    private String userServiceUrl;

    @Autowired
    public UserServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }


    public ResponseEntity<Void> registerUser(RegisterUserRq rq) {
        return restClient
                .post()
                .uri(String.format("%s/register", userServiceUrl))
                .body(rq)
                .retrieve()
                .toBodilessEntity();
    }

    public ResponseEntity<Void> loginUser(LoginUserRq rq) {
        return restClient
                .post()
                .uri(String.format("%s/login", userServiceUrl))
                .body(rq)
                .retrieve()
                .toBodilessEntity();
    }

    public ResponseEntity<LoginUserResponse> getUserInfo(Long userId) {
        return restClient
                .get()
                .uri(String.format("%s/personal/%d", userServiceUrl, userId))
                .retrieve()
                .toEntity(LoginUserResponse.class);
    }

    public ResponseEntity<List<UserDto>> getUserList() {
        return restClient
                .get()
                .uri(String.format("%s/users", userServiceUrl))
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });
    }

    public ResponseEntity<String> getAdminPanelHtml() {
        return restClient
                .get()
                .uri(String.format("%s/", userServiceUrl))
                .retrieve()
                .toEntity(String.class);
    }
}
