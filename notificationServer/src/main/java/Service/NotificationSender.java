package Service;

import DTO.ExternalDietNotificationDTO;

public interface NotificationSender {

    ExternalDietNotificationDTO fetchDietNotificationNotification(String url);
    void sendDietNotificationNotification(ExternalDietNotificationDTO dietNotificationDTO);
}
