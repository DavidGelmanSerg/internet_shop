package ru.gelman.notification_service.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.gelman.notification_service.dto.UserContactInfo;

import java.net.URI;
import java.util.List;

@Component
public class UserServiceClient {
    private final RestClient client;

    public UserServiceClient() {
        this.client = RestClient.create();
    }

    public List<UserContactInfo> getAdminList() {
        URI url = URI.create("localhost:8080/api/users/staff");
        return client.get().uri(url).retrieve().body(new ParameterizedTypeReference<>() {
        });
    }

    public UserContactInfo getEmailInfo(Long userId) {
        URI url = URI.create(String.format("localhost:8080/api/users/%d", userId));
        return client.get().uri(url).retrieve().body(UserContactInfo.class);
    }
}
