package com.example.notificationserver.Repository;

import com.example.notificationserver.Entity.ExternalPaymentInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalPaymentInfoRepository extends JpaRepository<ExternalPaymentInfoEntity, Long> {
}
