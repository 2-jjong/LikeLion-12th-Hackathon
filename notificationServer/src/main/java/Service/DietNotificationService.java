package Service;

import DTO.DietNotificationDTO;

import java.util.List;

public interface DietNotificationService {
    public DietNotificationDTO createDietNotification(DietNotificationDTO dietNotificationDTO);

    public DietNotificationDTO getDietNotificationById(Long id);

    public List<DietNotificationDTO> getDietNotificationsByUserId(Long userId);

    public DietNotificationDTO updateDietNotification(DietNotificationDTO dietNotificationDTO);

    public void deleteDietNotification(Long id);

}
