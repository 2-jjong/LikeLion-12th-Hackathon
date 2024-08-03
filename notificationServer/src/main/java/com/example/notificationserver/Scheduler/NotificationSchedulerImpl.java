package com.example.notificationserver.Scheduler;

import com.example.notificationserver.DAO.PaymentNotificationDAO;
import com.example.notificationserver.DTO.*;
import com.example.notificationserver.Entity.ExternalPaymentInfoEntity;
import com.example.notificationserver.Repository.ExternalPaymentInfoRepository;
import com.example.notificationserver.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
public class NotificationSchedulerImpl implements NotificationScheduler{

    private final NotificationSender notificationSender;
    private final NotificationTypeService notificationTypeService;
    private final DietNotificationService dietNotificationService;
    private final NotificationService notificationService;
    private final PaymentNotificationService paymentNotificationService;

    private final SurveyNotificationService surveyNotificationService;
    private final ExternalPaymentInfoRepository externalPaymentInfoRepository;
    private final CommunicationService communicationService;

    @Autowired
    public NotificationSchedulerImpl(NotificationSender notificationSender, NotificationTypeService notificationTypeService, DietNotificationService dietNotificationService, NotificationService notificationService, PaymentNotificationService paymentNotificationService, PaymentNotificationDAO paymentNotificationDAO, SurveyNotificationService surveyNotificationService, ExternalPaymentInfoRepository externalPaymentInfoRepository, CommunicationService communicationService) {
        this.notificationSender = notificationSender;
        this.notificationTypeService = notificationTypeService;
        this.dietNotificationService = dietNotificationService;
        this.notificationService = notificationService;
        this.paymentNotificationService = paymentNotificationService;
        this.surveyNotificationService = surveyNotificationService;
        this.externalPaymentInfoRepository = externalPaymentInfoRepository;
        this.communicationService = communicationService;
    }

    // 초 분 시 일 월 요일에 자동으로 실행되는 메서드
    //"0 0 7, 12, 23 * * ?" 7시 12시 23시
    //"0/20 * * * * ?" 20초마다 실행
    //토요일
    //@Scheduled(cron = "0 0 19 ? * SAT")    //식단 알림
    @Override
    //@Scheduled(cron = "0/9 * * * * ?")
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
        notification.setEmail("user@naver.com");
        notification.setNotificationContent(combinedContent);
        notification.setNotificationTime(currentDate);
        dietNotificationService.createDietNotification(notification);
        notificationService.sendNotification(notification);
    }

    @Override
    @Scheduled(cron = "0/5 * * * * ?")    // 결제일 알림
    public void scheduledPaymentNotificationTasks() {
        ExternalPaymentNotificationDTO userEmailsDTO = communicationService.getUserEmails();
        if (userEmailsDTO != null) {
            communicationService.saveToExternalPaymentInfoEntity(userEmailsDTO);
        }
        schedulePaymentNotification();
    }
    @Override
    public void schedulePaymentNotification() {
        NotificationTypeDTO notificationType = notificationTypeService.getNotificationTypeById(3L);
        LocalDateTime currentDate = LocalDateTime.now();

        List<ExternalPaymentInfoEntity> pendingNotifications = externalPaymentInfoRepository.findByProcessedFalseAndDate(LocalDate.now());

        for (ExternalPaymentInfoEntity paymentInfo : pendingNotifications) {
            String email = paymentInfo.getEmail();
            // 새로운 알림 생성
            PaymentNotificationDTO newNotification = PaymentNotificationDTO.builder()
                    .email(email)
                    .notificationContent(notificationType.getNotificationContent())
                    .notificationTime(currentDate)  // 현재 시각으로 설정
                    .build();

            // 알림 전송
            notificationService.sendPaymentNotification(newNotification);

            // 새로운 알림 저장
            paymentNotificationService.createPaymentNotification(newNotification);

            // processed를 true로 업데이트
            paymentInfo.setProcessed(true);
            externalPaymentInfoRepository.save(paymentInfo);
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
        notification.setEmail("응애");
        notification.setNotificationContent(combinedContent);
        notification.setNotificationTime(currentDate);

        // SurveyNotification 생성 및 전송
        surveyNotificationService.createSurveyNotification(notification);
        notificationService.sendSurveyNotification(notification);
    }
}