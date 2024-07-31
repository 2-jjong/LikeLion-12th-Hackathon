package com.example.notificationserver.DAO;

import com.example.notificationserver.DTO.PaymentNotificationDTO;
import com.example.notificationserver.Entity.PaymentNotificationEntity;
import com.example.notificationserver.Repository.PaymentNotificationRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class PaymentNotificationDAOImpl implements PaymentNotificationDAO {
    private final PaymentNotificationRepository paymentNotificationRepository;

    public PaymentNotificationDAOImpl(PaymentNotificationRepository paymentNotificationRepository) {
        this.paymentNotificationRepository = paymentNotificationRepository;
    }

    // Entity to DTO
    private PaymentNotificationDTO paymentNotificationEntityToDTO(PaymentNotificationEntity paymentNotificationEntity) {
        return PaymentNotificationDTO.builder()
                .id(paymentNotificationEntity.getId())
                .email(paymentNotificationEntity.getEmail())
                .userId(paymentNotificationEntity.getUserId())
                .notificationContent(paymentNotificationEntity.getNotificationContent())
                .lastPaymentDate(paymentNotificationEntity.getLastPaymentDate())
                .notificationTime(paymentNotificationEntity.getNotificationTime())
                .build();
    }

    // DTO to Entity
    private PaymentNotificationEntity paymentNotificationDTOToEntity(PaymentNotificationDTO paymentNotificationDTO) {
        return PaymentNotificationEntity.builder()
                .id(paymentNotificationDTO.getId())
                .userId(paymentNotificationDTO.getUserId())
                .email(paymentNotificationDTO.getEmail())
                .notificationContent(paymentNotificationDTO.getNotificationContent())
                .lastPaymentDate(paymentNotificationDTO.getLastPaymentDate())
                .notificationTime(paymentNotificationDTO.getNotificationTime())
                .build();
    }

    @Override
    public PaymentNotificationDTO create(PaymentNotificationDTO paymentNotificationDTO) {
        PaymentNotificationEntity paymentNotificationEntity = paymentNotificationDTOToEntity(paymentNotificationDTO);
        PaymentNotificationEntity savedEntity = paymentNotificationRepository.save(paymentNotificationEntity);
        return paymentNotificationEntityToDTO(savedEntity);
    }

    @Override
    public Optional<PaymentNotificationDTO> findLatestByEmail(String email) {
        Optional<PaymentNotificationEntity> latestEntity = paymentNotificationRepository.findFirstByEmailOrderByLastPaymentDateDesc(email);
        return latestEntity.map(this::paymentNotificationEntityToDTO);
    }

    @Override
    public void updateLastPaymentDate(PaymentNotificationDTO paymentNotificationDTO, LocalDateTime newPaymentDate) {
        Optional<PaymentNotificationEntity> entityOpt = paymentNotificationRepository.findById(paymentNotificationDTO.getId());
        if (entityOpt.isPresent()) {
            PaymentNotificationEntity paymentNotificationEntity = entityOpt.get();
            paymentNotificationEntity.setLastPaymentDate(newPaymentDate);
            paymentNotificationRepository.save(paymentNotificationEntity);
        }
    }

    @Override
    public void updateNotificationContentAndTime(PaymentNotificationDTO paymentNotificationDTO) {
        Optional<PaymentNotificationEntity> entityOpt = paymentNotificationRepository.findById(paymentNotificationDTO.getId());
        if (entityOpt.isPresent()) {
            PaymentNotificationEntity paymentNotificationEntity = entityOpt.get();
            paymentNotificationEntity.setNotificationContent(paymentNotificationDTO.getNotificationContent());
            paymentNotificationEntity.setNotificationTime(paymentNotificationDTO.getNotificationTime());
            paymentNotificationRepository.save(paymentNotificationEntity);
        }
    }

    @Override
    public PaymentNotificationDTO update(PaymentNotificationDTO paymentNotificationDTO) {
        Optional<PaymentNotificationEntity> entityOpt = paymentNotificationRepository.findById(paymentNotificationDTO.getId());
        if (entityOpt.isPresent()) {
            PaymentNotificationEntity paymentNotificationEntity = entityOpt.get();
            paymentNotificationEntity.setUserId(paymentNotificationDTO.getUserId());
            paymentNotificationEntity.setEmail(paymentNotificationDTO.getEmail());
            paymentNotificationEntity.setNotificationContent(paymentNotificationDTO.getNotificationContent());
            paymentNotificationEntity.setLastPaymentDate(paymentNotificationDTO.getLastPaymentDate());
            paymentNotificationEntity.setNotificationTime(paymentNotificationDTO.getNotificationTime());
            PaymentNotificationEntity updatedEntity = paymentNotificationRepository.save(paymentNotificationEntity);
            return paymentNotificationEntityToDTO(updatedEntity);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        paymentNotificationRepository.deleteById(id);
    }
}
