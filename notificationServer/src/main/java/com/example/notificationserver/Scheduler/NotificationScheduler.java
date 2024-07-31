package com.example.notificationserver.Scheduler;

import com.example.notificationserver.DAO.PaymentNotificationDAO;
import com.example.notificationserver.DTO.DietNotificationDTO;
import com.example.notificationserver.DTO.ExternalDietNotificationDTO;
import com.example.notificationserver.DTO.NotificationTypeDTO;
import com.example.notificationserver.DTO.PaymentNotificationDTO;
import com.example.notificationserver.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
public class NotificationScheduler {

    private final NotificationSender notificationSender;
    private final NotificationTypeService notificationTypeService;
    private final DietNotificationService dietNotificationService;
    private final NotificationService notificationService;
    private final PaymentNotificationService paymentNotificationService;
    private final PaymentNotificationDAO paymentNotificationDAO;

    @Autowired
    public NotificationScheduler(NotificationSender notificationSender, NotificationTypeService notificationTypeService, DietNotificationService dietNotificationService, NotificationService notificationService, PaymentNotificationService paymentNotificationService, PaymentNotificationDAO paymentNotificationDAO) {
        this.notificationSender = notificationSender;
        this.notificationTypeService = notificationTypeService;
        this.dietNotificationService = dietNotificationService;
        this.notificationService = notificationService;
        this.paymentNotificationService = paymentNotificationService;
        this.paymentNotificationDAO = paymentNotificationDAO;
    }

    //정보 가져오는거 (Maybe?)
    @Scheduled(cron = "0 0 7 * * *")
    public void fetchAndStoreDietNotificationNotification() {
        String url = "http://127.0.0.1:3000/notification/diet"; // 실제 서버 URL
        ExternalDietNotificationDTO externalDTO = notificationSender.fetchDietNotificationNotification(url);

        // 필요한 정보만 추출
        DietNotificationDTO dietNotificationDTO = new DietNotificationDTO();
        dietNotificationDTO.setUserId(externalDTO.getUserId());
        //여기에 내가 생각했던 내용물 저장하면 될 듯? Maybe... 어케하노 시@부럴
        dietNotificationDTO.setNotificationContent(externalDTO.getDiet());
        dietNotificationDTO.setNotificationContent(externalDTO.getNotificationContent());

        //DB에 저장시키기
        notificationSender.sendDietNotificationNotification(externalDTO);
    }

    // 초 분 시 일 월 년에 자동으로 실행되는 메서드
    //"0 0 7, 12, 23 * * ?" 7시 12시 23시
    //"0/20 * * * * ?" 20초마다 실행
    @Scheduled(cron = "0 1 * * * ?")    //식단 알림
    public void scheduleDietNotification() {
        // NotificationType 에서 ID 1번과 2번의 내용을 가져옴
        NotificationTypeDTO notificationType1 = notificationTypeService.getNotificationTypeById(1L);
        NotificationTypeDTO notificationType2 = notificationTypeService.getNotificationTypeById(2L);
        LocalDateTime currentDate = LocalDateTime.now();

        // 내용을 결합
        String combinedContent =
                notificationType1.getNotificationContent()
                        + " " + currentDate + " "
                        + notificationType2.getNotificationContent();

        DietNotificationDTO notification = new DietNotificationDTO();
        notification.setEmail("test@naver.com");
        notification.setNotificationContent(combinedContent);
        notification.setNotificationTime(currentDate);
        dietNotificationService.createDietNotification(notification);
        notificationService.sendNotification(notification);
    }

    @Scheduled(cron = "0 1 * * * ?")    //결제일 알림
    public void schedulePaymentNotification() {
        NotificationTypeDTO notificationType1 = notificationTypeService.getNotificationTypeById(3L);
        NotificationTypeDTO notificationType2 = notificationTypeService.getNotificationTypeById(4L);
        LocalDateTime currentDate = LocalDateTime.now();
        String email = "test@naver.com";
        //결제일 데이터 중 설정 email 가장 최신 결제일 값
        Optional<PaymentNotificationDTO> latestNotificationOpt = paymentNotificationService.findLatestByEmail(email);

        if (latestNotificationOpt.isPresent()) {
            PaymentNotificationDTO latestNotification = latestNotificationOpt.get();
            LocalDateTime lastPaymentDate = latestNotification.getLastPaymentDate();
            LocalDateTime nextPaymentDate = lastPaymentDate.plusDays(7);

            long daysRemaining = ChronoUnit.DAYS.between(currentDate, nextPaymentDate);
            //결제일 알림 내용 결합
            String contentWithDaysRemaining = notificationType1.getNotificationContent()
                    + " " + daysRemaining + " " +
                    notificationType2.getNotificationContent();

            PaymentNotificationDTO notification = new PaymentNotificationDTO();
            notification.setEmail(email);
            notification.setNotificationContent(contentWithDaysRemaining);
            notification.setNotificationTime(currentDate);
            paymentNotificationService.createPaymentNotification(notification);

            notificationService.sendPaymentNotification(notification);

            paymentNotificationDAO.updateLastPaymentDate(latestNotification, currentDate);
        }
    }
}