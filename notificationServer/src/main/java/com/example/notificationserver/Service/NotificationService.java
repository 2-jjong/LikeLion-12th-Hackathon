package com.example.notificationserver.Service;

import com.example.notificationserver.DTO.DietNotificationDTO;
import com.example.notificationserver.DTO.PaymentNotificationDTO;
import com.example.notificationserver.DTO.SurveyNotificationDTO;

public interface NotificationService {
    void sendNotification(DietNotificationDTO notification);
    void sendPaymentNotification(PaymentNotificationDTO notification);
    void sendSurveyNotification(SurveyNotificationDTO notification);
}
