package com.example.notificationserver.DAO;

import com.example.notificationserver.DTO.PaymentNotificationDTO;
import com.example.notificationserver.Entity.PaymentNotificationEntity;
import com.example.notificationserver.Repository.PaymentNotificationRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class PaymentNotificationDAOImpl implements PaymentNotificationDAO{
    private final PaymentNotificationRepository paymentNotificationRepository;

    public PaymentNotificationDAOImpl(PaymentNotificationRepository paymentNotificationRepository) {
        this.paymentNotificationRepository = paymentNotificationRepository;
    }

    //Entity to DTO
    private PaymentNotificationDTO PaymentNotificationEntityToDTO(PaymentNotificationEntity paymentNotificationEntity) {
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
        PaymentNotificationEntity paymentNotificationEntity = PaymentNotificationEntity.builder()
                .userId(paymentNotificationDTO.getUserId())
                .email(paymentNotificationDTO.getEmail())
                .lastPaymentDate(paymentNotificationDTO.getLastPaymentDate())
                .notificationTime(paymentNotificationDTO.getNotificationTime())
                .build();
        paymentNotificationRepository.save(paymentNotificationEntity);
        return null;
    }

    public Optional<PaymentNotificationDTO> findLatestByEmail(String email) {
        return paymentNotificationRepository.findLatestByEmail(email);
    }

    @Override
    public void updateLastPaymentDate(PaymentNotificationDTO paymentNotificationDTO, LocalDateTime newPaymentDate) {
        PaymentNotificationEntity paymentNotificationEntity = paymentNotificationDTOToEntity(paymentNotificationDTO);
        paymentNotificationEntity.setLastPaymentDate(newPaymentDate);
        paymentNotificationRepository.save(paymentNotificationEntity);
    }

    @Override
    public void delete(Long id) {
        paymentNotificationRepository.deleteById(id);
    }
}
