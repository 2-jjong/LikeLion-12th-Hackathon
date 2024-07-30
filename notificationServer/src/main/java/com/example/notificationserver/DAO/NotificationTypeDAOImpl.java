package com.example.notificationserver.DAO;

import com.example.notificationserver.DTO.NotificationTypeDTO;
import com.example.notificationserver.Entity.NotificationTypeEntity;
import com.example.notificationserver.Repository.NotificationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class NotificationTypeDAOImpl implements NotificationTypeDAO {

    private final NotificationTypeRepository notificationTypeRepository;

    @Autowired
    public NotificationTypeDAOImpl(NotificationTypeRepository notificationTypeRepository) {
        this.notificationTypeRepository = notificationTypeRepository;
    }

    // Entity to DTO
    private NotificationTypeDTO notificationTypeEntityToDTO(NotificationTypeEntity notificationTypeEntity) {
        return NotificationTypeDTO.builder()
                .id(notificationTypeEntity.getId())
                .notificationContent(notificationTypeEntity.getNotificationContent())
                .build();
    }

    // DTO to Entity
    private NotificationTypeEntity notificationTypeDTOToEntity(NotificationTypeDTO notificationTypeDTO) {
        return NotificationTypeEntity.builder()
                .notificationContent(notificationTypeDTO.getNotificationContent())
                .build();
    }

    @Override
    public NotificationTypeDTO create(NotificationTypeDTO notificationTypeDTO) {
        NotificationTypeEntity notificationTypeEntity = notificationTypeDTOToEntity(notificationTypeDTO);
        notificationTypeRepository.save(notificationTypeEntity);
        return notificationTypeEntityToDTO(notificationTypeEntity);
    }

    @Override
    public NotificationTypeDTO findById(Long id) {
        Optional<NotificationTypeEntity> notificationTypeEntity = notificationTypeRepository.findById(id);
        return notificationTypeEntity.map(this::notificationTypeEntityToDTO).orElse(null);
    }

    @Override
    public NotificationTypeDTO update(NotificationTypeDTO notificationTypeDTO) {
        NotificationTypeEntity notificationTypeEntity = notificationTypeRepository.findById(notificationTypeDTO.getId()).orElse(null);
        if (notificationTypeEntity != null) {
            notificationTypeEntity.setNotificationContent(notificationTypeDTO.getNotificationContent());
            notificationTypeRepository.save(notificationTypeEntity);
            return notificationTypeEntityToDTO(notificationTypeEntity);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        notificationTypeRepository.deleteById(id);
    }
}
