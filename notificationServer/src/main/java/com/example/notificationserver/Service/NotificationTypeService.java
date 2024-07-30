package com.example.notificationserver.Service;

import com.example.notificationserver.DTO.NotificationTypeDTO;

public interface NotificationTypeService {
    NotificationTypeDTO createNotificationType(NotificationTypeDTO notificationTypeDTO);
    NotificationTypeDTO getNotificationTypeById(Long id);
    NotificationTypeDTO updateNotificationType(NotificationTypeDTO notificationTypeDTO);
    void deleteNotificationType(Long id);
}
