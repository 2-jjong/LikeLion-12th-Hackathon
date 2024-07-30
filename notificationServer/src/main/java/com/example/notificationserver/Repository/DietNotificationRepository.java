package com.example.notificationserver.Repository;

import com.example.notificationserver.Entity.DietNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DietNotificationRepository extends JpaRepository<DietNotificationEntity, Long> {
    List<DietNotificationEntity> findByUserId(Long userId);
}
