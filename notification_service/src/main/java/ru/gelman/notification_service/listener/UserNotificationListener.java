package ru.gelman.notification_service.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.gelman.notification_service.events.UserLoginEvent;
import ru.gelman.notification_service.events.UserRegisterEvent;
import ru.gelman.notification_service.service.NotificationService;

@Component
@KafkaListener(
        topics = "user-event-topic",
        id = "userNotificationListener",
        containerFactory = "kafkaListenerContainerFactory"
)
public class UserNotificationListener {
    private final NotificationService notificationService;

    @Autowired
    public UserNotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaHandler
    public void handleUserRegister(UserRegisterEvent event) {
        notificationService.sendUserRegisterNotification(event);
    }

    @KafkaHandler
    public void handleUserLogin(UserLoginEvent event) {
        notificationService.sendUserLoginNotification(event);
    }
}
