package com.example.notificationserver.Service;

import com.example.notificationserver.DAO.DietNotificationDAO;
import com.example.notificationserver.DTO.DietNotificationDTO;
import com.example.notificationserver.DTO.NotificationTypeDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DietNotificationServiceImpl implements DietNotificationService {

    private final DietNotificationDAO dietNotificationDAO;
    private final NotificationService notificationService;
    private final NotificationTypeService notificationTypeService;

    public DietNotificationServiceImpl(DietNotificationDAO dietNotificationDAO, NotificationService notificationService, NotificationTypeService notificationTypeService) {
        this.dietNotificationDAO = dietNotificationDAO;
        this.notificationService = notificationService;
        this.notificationTypeService = notificationTypeService;
    }

    @Override
    public DietNotificationDTO createDietNotification(DietNotificationDTO dietNotificationDTO) {
        return dietNotificationDAO.create(dietNotificationDTO);
    }

    @Override
    public DietNotificationDTO getDietNotificationById(Long id) {
        return dietNotificationDAO.findById(id);
    }

    @Override
    public List<DietNotificationDTO> getDietNotificationsByUserId(Long userId) {
        return dietNotificationDAO.findByUserId(userId);
    }

    @Override
    public DietNotificationDTO updateDietNotification(DietNotificationDTO dietNotificationDTO) {
        return dietNotificationDAO.update(dietNotificationDTO);
    }

    @Override
    public void deleteDietNotification(Long id) {
        dietNotificationDAO.delete(id);
    }

    // 초 분 시 일 월 년에 자동으로 실행되는 메서드
    //"0 0 7, 12, 23 * * ?" 7시 12시 23시
    //"0/20 * * * * ?" 20초마다 실행
    @Override
    @Scheduled(cron = "0/1 * * * * ?")
    public void scheduleDietNotification() {
        // NotificationType 에서 ID 1번과 4번의 내용을 가져옴
        NotificationTypeDTO notificationType1 = notificationTypeService.getNotificationTypeById(1L);
        NotificationTypeDTO notificationType4 = notificationTypeService.getNotificationTypeById(2L);
        LocalDateTime currentDate = LocalDateTime.now();

        // 내용을 결합
        String combinedContent =
                notificationType1.getNotificationContent()
                + " " + currentDate + " "
                + notificationType4.getNotificationContent();

        DietNotificationDTO notification = new DietNotificationDTO();
        notification.setEmail("test@naver.com");
        notification.setNotificationContent(combinedContent);
        notification.setNotificationTime(currentDate);
        createDietNotification(notification);
        notificationService.sendNotification(notification);
    }
}