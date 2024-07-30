package com.example.notificationserver.Service;

import com.example.notificationserver.DAO.NotificationTypeDAO;
import com.example.notificationserver.DTO.NotificationTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationTypeServiceImpl implements NotificationTypeService {

    private final NotificationTypeDAO notificationTypeDAO;

    @Autowired
    public NotificationTypeServiceImpl(NotificationTypeDAO notificationTypeDAO) {
        this.notificationTypeDAO = notificationTypeDAO;
    }

    @Override
    public NotificationTypeDTO createNotificationType(NotificationTypeDTO notificationTypeDTO) {
        return notificationTypeDAO.create(notificationTypeDTO);
    }

    @Override
    public NotificationTypeDTO getNotificationTypeById(Long id) {
        return notificationTypeDAO.findById(id);
    }


    @Override
    public NotificationTypeDTO updateNotificationType(NotificationTypeDTO notificationTypeDTO) {
        return notificationTypeDAO.update(notificationTypeDTO);
    }

    @Override
    public void deleteNotificationType(Long id) {
        notificationTypeDAO.delete(id);
    }
}
