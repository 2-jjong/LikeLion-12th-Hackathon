package com.example.notificationserver.Service;

import com.example.notificationserver.DAO.PaymentNotificationDAO;
import com.example.notificationserver.DTO.NotificationTypeDTO;
import com.example.notificationserver.DTO.PaymentNotificationDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentNotificationServiceImpl implements PaymentNotificationService{

    private final PaymentNotificationDAO paymentNotificationDAO;

    public PaymentNotificationServiceImpl(PaymentNotificationDAO paymentNotificationDAO) {
        this.paymentNotificationDAO = paymentNotificationDAO;

    }

    @Override
    public PaymentNotificationDTO createPaymentNotification(PaymentNotificationDTO paymentNotificationDTO) {
        return paymentNotificationDAO.create(paymentNotificationDTO);
    }

    @Override
    public void deletePaymentNotification(Long id) {
        paymentNotificationDAO.delete(id);
    }

//    @Override
//    @Scheduled(cron = "0 1 * * * ?")
//    public void schedulePaymentNotification() {
//        // NotificationType 에서 ID 3번과 4번의 내용을 가져옴
//        NotificationTypeDTO notificationType1 = notificationTypeService.getNotificationTypeById(3L);
//        NotificationTypeDTO notificationType2 = notificationTypeService.getNotificationTypeById(4L);
//        LocalDateTime currentDate = LocalDateTime.now();
//
//        // 내용을 결합
//        String combinedContent =
//                notificationType1.getNotificationContent()
//                        + " " + currentDate + " "
//                        + notificationType2.getNotificationContent();
//
//        PaymentNotificationDTO notification = new PaymentNotificationDTO();
//        notification.setEmail("test@naver.com");
//        notification.setNotificationContent(combinedContent);
//        notification.setNotificationTime(currentDate);
//        createPaymentNotification(notification);
//        notificationService.sendPaymentNotification(notification);
//    }
}
