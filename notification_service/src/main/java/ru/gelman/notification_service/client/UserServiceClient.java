package ru.gelman.notification_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.gelman.notification_service.dto.UserContactInfo;

import java.net.URI;
import java.util.List;

@Component
public class UserServiceClient {
    private final RestClient client;

    @Value("${app.services.users.url}")
    private String userServiceUrl;

    public UserServiceClient() {
        this.client = RestClient.create();
    }

    public List<UserContactInfo> getAdminList() {
        URI url = URI.create(userServiceUrl + "/api/users/staff");
        return client.get().uri(url).retrieve().body(new ParameterizedTypeReference<>() {
        });
    }

    public UserContactInfo getEmailInfo(Long userId) {
        URI url = URI.create(String.format("%s/api/users/%d", userServiceUrl, userId));
        return client.get().uri(url).retrieve().body(UserContactInfo.class);
    }
}
