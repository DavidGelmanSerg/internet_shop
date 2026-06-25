package ru.gelman.notification_service.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.gelman.notification_service.events.OrderChangeStatusEvent;
import ru.gelman.notification_service.events.OrderCreatedEvent;
import ru.gelman.notification_service.service.NotificationService;

@Component
@KafkaListener(
        topics = "order-event-topic",
        id = "orderNotificationListener",
        containerFactory = "kafkaListenerContainerFactory"
)
public class OrderNotificationListener {
    private final NotificationService notificationService;

    @Autowired
    public OrderNotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaHandler
    public void handleOrderCreated(OrderCreatedEvent event) {
        notificationService.sendOrderCreatedNotification(event);
    }

    @KafkaHandler
    public void handleOrderStatusChanged(OrderChangeStatusEvent event) {
        notificationService.sendOrderStatusChangedNotification(event);
    }
}
