package com.example.notificationserver.Service;

import com.example.notificationserver.DTO.DietNotificationDTO;
import com.example.notificationserver.DTO.PaymentNotificationDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNotification(DietNotificationDTO notification) {
        messagingTemplate.convertAndSend("/topic/notification/diet/" + notification.getEmail(), notification);
    }
    public void sendPaymentNotification(PaymentNotificationDTO notification) {
        messagingTemplate.convertAndSend("/topic/notification/payment/" + notification.getEmail(), notification);
    }
}