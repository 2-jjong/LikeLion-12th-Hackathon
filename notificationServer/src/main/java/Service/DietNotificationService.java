package Service;

import DTO.DietNotificationDTO;

import java.util.List;

public interface DietNotificationService {
    public DietNotificationDTO createFoodMenu(DietNotificationDTO dietNotificationDTO);

    public DietNotificationDTO getFoodMenuById(Long id);

    public List<DietNotificationDTO> getFoodMenusByUserId(Long userId);

    public DietNotificationDTO updateFoodMenu(DietNotificationDTO dietNotificationDTO);

    public void deleteFoodMenu(Long id);

    public void sendFoodMenuNotification(DietNotificationDTO dietNotificationDTO);
}
