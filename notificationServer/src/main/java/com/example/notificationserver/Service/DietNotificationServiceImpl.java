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

}