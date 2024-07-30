package com.example.notificationserver.DAO;

import com.example.notificationserver.DTO.DietNotificationDTO;

import java.util.List;

public interface DietNotificationDAO {
    DietNotificationDTO create(DietNotificationDTO dietNotificationDTO);
    DietNotificationDTO findById(Long id);
    List<DietNotificationDTO> findByUserId(Long userId);
    DietNotificationDTO update(DietNotificationDTO dietNotificationDTO);
    void delete(Long id);
    //List<DietNotificationDTO> findByUserId(Long userId);
}
