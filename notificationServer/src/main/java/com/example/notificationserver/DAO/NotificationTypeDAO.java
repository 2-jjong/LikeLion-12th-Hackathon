package com.example.notificationserver.DAO;

import com.example.notificationserver.DTO.NotificationTypeDTO;

import java.util.List;

public interface NotificationTypeDAO {
    NotificationTypeDTO create(NotificationTypeDTO NotificationTypeDTO);
    NotificationTypeDTO findById(Long id);
    NotificationTypeDTO update(NotificationTypeDTO NotificationTypeDTO);
    void delete(Long id);
    //List<NotificationTypeDTO> findByUserId(Long userId);
}
