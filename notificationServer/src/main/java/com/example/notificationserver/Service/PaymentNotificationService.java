package com.example.notificationserver.Service;

import com.example.notificationserver.DTO.PaymentNotificationDTO;

import java.util.Optional;

public interface PaymentNotificationService {
    PaymentNotificationDTO createPaymentNotification(PaymentNotificationDTO paymentNotificationDTO);
    void deletePaymentNotification(Long id);

    Optional<PaymentNotificationDTO> findLatestByEmail(String email);
}
