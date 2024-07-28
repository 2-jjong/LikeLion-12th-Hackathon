package DAO;

import DTO.DietNotificationDTO;

import java.util.List;

public interface DietNotificationDAO {
    public DietNotificationDTO create(DietNotificationDTO dietNotificationDTO);
    public DietNotificationDTO findById(Long id);
    public List<DietNotificationDTO> findByUserId(Long userId);
    public DietNotificationDTO update(DietNotificationDTO dietNotificationDTO);
    public void delete(Long id);
    //List<DietNotificationDTO> findByUserId(Long userId);
}
