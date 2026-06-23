package ru.gelman.notification_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.gelman.notification_service.client.UserServiceClient;
import ru.gelman.notification_service.dto.UserContactInfo;
import ru.gelman.notification_service.events.OrderChangeStatusEvent;
import ru.gelman.notification_service.events.OrderCreatedEvent;
import ru.gelman.notification_service.events.UserLoginEvent;
import ru.gelman.notification_service.events.UserRegisterEvent;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private final UserServiceClient userServiceClient;
    private final JavaMailSender email;
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public NotificationService(UserServiceClient userServiceClient, JavaMailSender email) {
        this.userServiceClient = userServiceClient;
        this.email = email;
    }

    private List<UserContactInfo> getSendListEmails(Long userId) {
        List<UserContactInfo> emails = new ArrayList<>(userServiceClient.getAdminList());
        emails.add(userServiceClient.getEmailInfo(userId));
        return emails;
    }

    public void sendOrderCreatedNotification(OrderCreatedEvent event) {
        List<UserContactInfo> emails = getSendListEmails(event.userId());
        for (UserContactInfo emailInfo : emails) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(emailInfo.email());
            message.setText(String.format("Заказ %d успешно создан", event.orderId()));
            email.send(message);
        }
    }

    public void sendOrderStatusChangedNotification(OrderChangeStatusEvent event) {
        List<UserContactInfo> emails = getSendListEmails(event.userId());
        for (UserContactInfo emailInfo : emails) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(emailInfo.email());
            message.setText(String.format("Статус заказа %d успешно обновлен на %s", event.orderId(), event.newStatus()));
            email.send(message);
        }
    }

    public void sendUserRegisterNotification(UserRegisterEvent event) {
        List<UserContactInfo> emails = getSendListEmails(event.userId());
        for (UserContactInfo emailInfo : emails) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(emailInfo.email());
            message.setText("Вы успешно зарегистрировались");
            email.send(message);
        }
    }

    public void sendUserLoginNotification(UserLoginEvent event) {
        List<UserContactInfo> emails = getSendListEmails(event.userId());
        for (UserContactInfo emailInfo : emails) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(emailInfo.email());
            message.setText("Вы успешно вошли в аккаунт");
            email.send(message);
        }
    }
}
