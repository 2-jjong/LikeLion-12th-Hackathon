package DAO;

import DTO.DietNotificationDTO;
import Entity.DietNotificationEntity;
import Repository.DietNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DietNotificationDAOImpl implements DietNotificationDAO {
    private final DietNotificationRepository dietNotificationRepository;
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public DietNotificationDAOImpl(DietNotificationRepository dietNotificationRepository, JdbcTemplate jdbcTemplate){
        this.dietNotificationRepository = dietNotificationRepository;
        this.jdbcTemplate = jdbcTemplate;
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
/*
    @Override
    public List<DietNotificationDTO> findByUserId(Long userId) {
        String sql = "SELECT * FROM diet_notifications WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) ->
                new DietNotificationDTO(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("notification_content")
                )
        );

    }
*/
}
