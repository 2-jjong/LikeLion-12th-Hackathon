package DAO;

import DTO.DietNotificationDTO;
import Entity.DietNotificationEntity;
import Repository.DietNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DietNotificationDAOImpl implements DietNotificationDAO {
    private final DietNotificationRepository dietNotificationRepository;

    @Autowired
    public DietNotificationDAOImpl(DietNotificationRepository dietNotificationRepository){
        this.dietNotificationRepository = dietNotificationRepository;
    }
    //Entity To DTO
    private DietNotificationDTO DietNotificationEntityToDTO(DietNotificationEntity dietNotificationEntity) {
        return DietNotificationDTO.builder()
                .id(dietNotificationEntity.getId())
                .userId(dietNotificationEntity.getUserId())
                .notificationContent(dietNotificationEntity.getNotificationContent())
                .notificationTime(dietNotificationEntity.getNotificationTime())
                .build();
    }

    @Override
    public DietNotificationDTO create(DietNotificationDTO dietNotificationDTO) {
        DietNotificationEntity dietNotificationEntity = DietNotificationEntity.builder()
                .userId(dietNotificationDTO.getUserId())
                .notificationContent(dietNotificationDTO.getNotificationContent())
                .notificationTime(dietNotificationDTO.getNotificationTime())
                .build();
        dietNotificationRepository.save(dietNotificationEntity);
        return dietNotificationDTO;
    }

    @Override
    public DietNotificationDTO findById(Long id) {
        DietNotificationEntity dietNotificationEntity = dietNotificationRepository.findById(id).orElse(null);
        if (dietNotificationEntity == null){
            return null;
        }
        return DietNotificationEntityToDTO(dietNotificationEntity);
    }

    @Override
    public List<DietNotificationDTO> findByUserId(Long userId) {
        List<DietNotificationEntity> DietNotificationEntities = dietNotificationRepository.findByUserId(userId);
        return DietNotificationEntities.stream()
                .map(this::DietNotificationEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DietNotificationDTO update(DietNotificationDTO dietNotificationDTO) {
        DietNotificationEntity dietNotificationEntity = dietNotificationRepository.findById(dietNotificationDTO.getId()).orElse(null);
        if(dietNotificationEntity != null){
            dietNotificationEntity.setNotificationContent(dietNotificationDTO.getNotificationContent());
            dietNotificationEntity.setNotificationTime(dietNotificationDTO.getNotificationTime());
            dietNotificationRepository.save(dietNotificationEntity);
        }
        return dietNotificationDTO;
    }

    @Override
    public void delete(Long id) {
        dietNotificationRepository.deleteById(id);
    }
}
