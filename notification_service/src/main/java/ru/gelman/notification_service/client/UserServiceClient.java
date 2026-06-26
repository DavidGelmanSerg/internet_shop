package ru.gelman.notification_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.gelman.notification_service.dto.UserContactInfo;

import java.util.List;

@Component
public class UserServiceClient {
    private final RestClient client;

    public UserServiceClient(
            @Value("${app.services.users.url}") String usersServiceUrl,
            @Value("${app.services.users.username}") String username,
            @Value("${app.services.users.password}") String password
    ) {
        this.client = RestClient.builder()
                .baseUrl(usersServiceUrl)
                .requestInterceptor((request, body, execution) -> {
                    request.getHeaders().setBasicAuth(username, password);
                    return execution.execute(request, body);
                })
                .build();
    }

    public List<UserContactInfo> getAdminList() {
        return client.get().uri("/api/users/staff").retrieve().body(new ParameterizedTypeReference<>() {
        });
    }

    public UserContactInfo getEmailInfo(Long userId) {
        return client.get().uri("/api/users/{userId}", userId).retrieve().body(UserContactInfo.class);
    }
}
