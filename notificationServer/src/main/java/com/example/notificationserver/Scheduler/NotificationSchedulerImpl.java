package com.example.notificationserver.Scheduler;

import com.example.notificationserver.DAO.PaymentNotificationDAO;
import com.example.notificationserver.DTO.*;
import com.example.notificationserver.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
public class NotificationSchedulerImpl implements NotificationScheduler{

    private final NotificationSender notificationSender;
    private final NotificationTypeService notificationTypeService;
    private final DietNotificationService dietNotificationService;
    private final NotificationService notificationService;
    private final PaymentNotificationService paymentNotificationService;

    private final SurveyNotificationService surveyNotificationService;

    @Autowired
    public NotificationSchedulerImpl(NotificationSender notificationSender, NotificationTypeService notificationTypeService, DietNotificationService dietNotificationService, NotificationService notificationService, PaymentNotificationService paymentNotificationService, PaymentNotificationDAO paymentNotificationDAO, SurveyNotificationService surveyNotificationService) {
        this.notificationSender = notificationSender;
        this.notificationTypeService = notificationTypeService;
        this.dietNotificationService = dietNotificationService;
        this.notificationService = notificationService;
        this.paymentNotificationService = paymentNotificationService;
        this.surveyNotificationService = surveyNotificationService;
    }

    //정보 가져오는거 (Maybe?)
    @Scheduled(cron = "0 0 7 * * *")
    @Override
    public void fetchAndStorePaymentNotificationNotification() {
        String url = "http://127.0.0.1:3000/notification/diet"; // 실제 서버 URL
        ExternalPaymentNotificationDTO externalDTO = notificationSender.fetchPaymentNotificationNotification(url);

        // 필요한 정보만 추출
        PaymentNotificationDTO paymentNotificationDTO = new PaymentNotificationDTO();
        paymentNotificationDTO.setEmail(externalDTO.getEmail());
        //여기에 내가 생각했던 내용물 저장하면 될 듯? Maybe... 어케하노 시@부럴
        paymentNotificationDTO.setLastPaymentDate(externalDTO.getLastPaymentDate());

        //DB에 저장시키기
        notificationSender.sendPaymentNotificationNotification(externalDTO);
    }

    // 초 분 시 일 월 년에 자동으로 실행되는 메서드
    //"0 0 7, 12, 23 * * ?" 7시 12시 23시
    //"0/20 * * * * ?" 20초마다 실행
    //토요일
    //@Scheduled(cron = "0 0 19 ? * SAT")    //식단 알림
    @Override
    @Scheduled(cron = "0/1 * * * * ?")
    public void scheduleDietNotification() {
        // NotificationType 에서 ID 1번과 2번의 내용을 가져옴
        NotificationTypeDTO notificationType1 = notificationTypeService.getNotificationTypeById(1L);
        NotificationTypeDTO notificationType2 = notificationTypeService.getNotificationTypeById(2L);
        LocalDateTime currentDate = LocalDateTime.now();
        // 포맷 정의: yyyy-MM-dd HH:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss");
        String formattedCurrentDate = currentDate.format(formatter);

        // 내용을 결합
        String combinedContent =
                notificationType1.getNotificationContent()
                        + " " + formattedCurrentDate + " "
                        + notificationType2.getNotificationContent();

        DietNotificationDTO notification = new DietNotificationDTO();
        notification.setEmail("son@naver.com");
        notification.setNotificationContent(combinedContent);
        notification.setNotificationTime(currentDate);
        dietNotificationService.createDietNotification(notification);
        notificationService.sendNotification(notification);
    }

    @Override
    @Scheduled(cron = "1 * * * * ?")    // 결제일 알림
    public void schedulePaymentNotification() {
        NotificationTypeDTO notificationType1 = notificationTypeService.getNotificationTypeById(3L);
        NotificationTypeDTO notificationType2 = notificationTypeService.getNotificationTypeById(4L);
        LocalDateTime currentDate = LocalDateTime.now();
        String email = "son@naver.com";
        // 결제일 데이터 중 설정 email 가장 최신 결제일 값
        Optional<PaymentNotificationDTO> latestNotificationOpt = paymentNotificationService.findLatestByEmail(email);

        if (latestNotificationOpt.isPresent()) {
            PaymentNotificationDTO latestNotification = latestNotificationOpt.get();
            LocalDateTime lastPaymentDate = latestNotification.getLastPaymentDate();
            LocalDateTime nextPaymentDate = lastPaymentDate.plusDays(7);

            long daysRemaining = ChronoUnit.DAYS.between(currentDate, nextPaymentDate);
            // 결제일 알림 내용 결합
            String contentWithDaysRemaining = notificationType1.getNotificationContent()
                    + " " + daysRemaining +
                    notificationType2.getNotificationContent();

            // 새로운 알림 생성
            PaymentNotificationDTO newNotification = PaymentNotificationDTO.builder()
                    .email(email)
                    .notificationContent(contentWithDaysRemaining)
                    .lastPaymentDate(lastPaymentDate)  // 동일한 lastPaymentTime 사용
                    .notificationTime(currentDate)  // 현재 시각으로 설정
                    .build();

            // 알림 전송
            notificationService.sendPaymentNotification(newNotification);

            // 새로운 알림 저장
            paymentNotificationService.createPaymentNotification(newNotification);
        }
    }

    //설문조사 알림
    @Override
    @Scheduled(cron = "0/10 * * * * *")
    public void scheduledSurveyNotifications() {
        // NotificationType에서 ID 5번의 내용을 가져옴
        NotificationTypeDTO notificationType = notificationTypeService.getNotificationTypeById(5L);
        LocalDateTime currentDate = LocalDateTime.now();
        // 포맷 정의: MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일");
        String formattedCurrentDate = currentDate.format(formatter);

        // 내용을 결합
        String combinedContent = formattedCurrentDate + " " + notificationType.getNotificationContent();

        // SurveyNotificationDTO 생성 및 설정
        SurveyNotificationDTO notification = new SurveyNotificationDTO();
        notification.setEmail(dietNotificationService.getLatestDietNotificationToString());
        notification.setNotificationContent(combinedContent);
        notification.setNotificationTime(currentDate);

        // SurveyNotification 생성 및 전송
        surveyNotificationService.createSurveyNotification(notification);
        notificationService.sendSurveyNotification(notification);
    }

}