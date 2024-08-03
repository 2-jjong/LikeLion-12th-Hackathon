package com.example.notificationserver.Service;

import com.example.notificationserver.DTO.DietNotificationDTO;
import com.example.notificationserver.DTO.PaymentNotificationDTO;
import com.example.notificationserver.DTO.SurveyNotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

public interface NotificationService {
    void sendNotification(DietNotificationDTO notification);
    void sendPaymentNotification(PaymentNotificationDTO notification);
    void sendSurveyNotification(SurveyNotificationDTO notification);
}
