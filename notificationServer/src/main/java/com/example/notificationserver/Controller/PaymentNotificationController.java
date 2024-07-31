package com.example.notificationserver.Controller;

import com.example.notificationserver.DTO.DietNotificationDTO;
import com.example.notificationserver.DTO.PaymentNotificationDTO;
import com.example.notificationserver.Service.NotificationService;
import com.example.notificationserver.Service.PaymentNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification/payment")
public class PaymentNotificationController {
    private final PaymentNotificationService paymentNotificationService;
    private final NotificationService notificationService;

    public PaymentNotificationController(PaymentNotificationService paymentNotificationService, NotificationService notificationService) {
        this.paymentNotificationService = paymentNotificationService;
        this.notificationService = notificationService;
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentNotificationDTO> createDietNotification(@RequestBody PaymentNotificationDTO paymentNotificationDTO) {
        PaymentNotificationDTO createdPaymentNotification = paymentNotificationService.createPaymentNotification(paymentNotificationDTO);
        notificationService.sendPaymentNotification(createdPaymentNotification); // 알림 전송
        return ResponseEntity.ok(createdPaymentNotification);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentNotification(@PathVariable Long id) {
        paymentNotificationService.deletePaymentNotification(id);
        return ResponseEntity.noContent().build();
    }
}
