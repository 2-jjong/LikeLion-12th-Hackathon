package com.example.notificationserver.Service;

import com.example.notificationserver.DTO.ExternalDietNotificationDTO;

public interface NotificationSender {

    ExternalDietNotificationDTO fetchDietNotificationNotification(String url);
    void sendDietNotificationNotification(ExternalDietNotificationDTO dietNotificationDTO);
}
