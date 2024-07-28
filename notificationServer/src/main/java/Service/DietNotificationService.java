package Service;

import DTO.DietNotificationDTO;

import java.util.List;

public interface DietNotificationService {
    DietNotificationDTO createDietNotification(DietNotificationDTO dietNotificationDTO);

    DietNotificationDTO getDietNotificationById(Long id);

    List<DietNotificationDTO> getDietNotificationsByUserId(Long userId);

    DietNotificationDTO updateDietNotification(DietNotificationDTO dietNotificationDTO);

    void deleteDietNotification(Long id);

}
