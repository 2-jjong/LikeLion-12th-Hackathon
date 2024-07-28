package Service;

import DTO.DietNotificationDTO;
import DTO.ExternalDietNotificationDTO;

public interface NotificationSender {

    public ExternalDietNotificationDTO fetchDietNotificationNotification(String url);
    public void sendDietNotificationNotification(ExternalDietNotificationDTO dietNotificationDTO);
}
