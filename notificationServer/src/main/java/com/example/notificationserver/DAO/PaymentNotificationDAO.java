package com.example.notificationserver.DAO;

import com.example.notificationserver.DTO.DietNotificationDTO;
import com.example.notificationserver.DTO.PaymentNotificationDTO;

import java.time.LocalDateTime;

public interface PaymentNotificationDAO {
    PaymentNotificationDTO create(PaymentNotificationDTO paymentNotificationDTO);
    void updateLastPaymentDate(PaymentNotificationDTO paymentNotificationDTO, LocalDateTime newPaymentDate);
    void delete(Long id);
}
