package com.example.notificationserver.Scheduler;

import com.example.notificationserver.DAO.PaymentNotificationDAO;
import com.example.notificationserver.DTO.*;
import com.example.notificationserver.Entity.ExternalDietInfoEntity;
import com.example.notificationserver.Entity.ExternalPaymentInfoEntity;
import com.example.notificationserver.Repository.ExternalDietInfoRepository;
import com.example.notificationserver.Repository.ExternalPaymentInfoRepository;
import com.example.notificationserver.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class NotificationSchedulerImpl implements NotificationScheduler{

    private final DietNotificationService dietNotificationService;
    private final NotificationService notificationService;
    private final PaymentNotificationService paymentNotificationService;

    private final SurveyNotificationService surveyNotificationService;
    private final ExternalPaymentInfoRepository externalPaymentInfoRepository;
    private final CommunicationService communicationService;
    private final ExternalDietInfoRepository externalDietInfoRepository;

    @Autowired
    public NotificationSchedulerImpl(DietNotificationService dietNotificationService, NotificationService notificationService, PaymentNotificationService paymentNotificationService, PaymentNotificationDAO paymentNotificationDAO, SurveyNotificationService surveyNotificationService, ExternalPaymentInfoRepository externalPaymentInfoRepository, CommunicationService communicationService, ExternalDietInfoRepository externalDietInfoRepository) {
        this.dietNotificationService = dietNotificationService;
        this.notificationService = notificationService;
        this.paymentNotificationService = paymentNotificationService;
        this.surveyNotificationService = surveyNotificationService;
        this.externalPaymentInfoRepository = externalPaymentInfoRepository;
        this.communicationService = communicationService;
        this.externalDietInfoRepository = externalDietInfoRepository;
    }

    // 초 분 시 일 월 요일에 자동으로 실행되는 메서드
    //"0 0 7, 12, 23 * * ?" 7시 12시 23시
    //"0/20 * * * * ?" 20초마다 실행
    //토요일
    @Override
    @Scheduled(cron = "0 0 7 * * ?")    // 금일 Diet 정보 받아오기
    public void scheduledDietNotificationTasks() {
        LocalDate today = LocalDate.now();
        communicationService.getUserDietEmails(today.toString());
    }

    @Override
    @Scheduled(cron = "0 0 7 * * ?") // 아침 알림: 매일 7시에 실행
    public void scheduleBreakfastNotification() {
        sendDietNotification("아침");
    }

    @Override
    @Scheduled(cron = "0 0 12 * * ?") // 점심 알림: 매일 12시에 실행
    public void scheduleLunchNotification() {
        sendDietNotification("점심");
    }

    @Override
    @Scheduled(cron = "0 0 18 * * ?") // 저녁 알림: 매일 18시에 실행
    public void scheduleDinnerNotification() {
        sendDietNotification("저녁");
    }

    @Override
    public void sendDietNotification(String mealTime) {
        LocalDate today = LocalDate.now();
        List<ExternalDietInfoEntity> diets = externalDietInfoRepository.findByDate(today.toString());

        for (ExternalDietInfoEntity diet : diets) {
            boolean shouldNotify = false;
            if ("아침".equals(mealTime) && !diet.isProcessedBreakfast() && diet.getBreakfast() != null) {
                shouldNotify = true;
            } else if ("점심".equals(mealTime) && !diet.isProcessedLunch() && diet.getLunch() != null) {
                shouldNotify = true;
            } else if ("저녁".equals(mealTime) && !diet.isProcessedDinner() && diet.getDinner() != null) {
                shouldNotify = true;
            }

            if (shouldNotify) {
                LocalDateTime currentDate = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일");
                String formattedCurrentDate = currentDate.format(formatter);

                StringBuilder combinedContent = new StringBuilder(formattedCurrentDate + " 식단은 ");

                if ("아침".equals(mealTime)) {
                    combinedContent.append(diet.getBreakfast());
                } else if ("점심".equals(mealTime)) {
                    combinedContent.append(diet.getLunch());
                } else if ("저녁".equals(mealTime)) {
                    combinedContent.append(diet.getDinner());
                }

                combinedContent.append(" 입니다~");

                DietNotificationDTO notification = new DietNotificationDTO();
                notification.setEmail(diet.getEmail());
                notification.setNotificationContent(combinedContent.toString());
                notification.setNotificationTime(currentDate);

                dietNotificationService.createDietNotification(notification);
                notificationService.sendNotification(notification);

                if ("아침".equals(mealTime)) {
                    diet.setProcessedBreakfast(true);
                } else if ("점심".equals(mealTime)) {
                    diet.setProcessedLunch(true);
                } else if ("저녁".equals(mealTime)) {
                    diet.setProcessedDinner(true);
                }
                externalDietInfoRepository.save(diet);
            }
        }
    }

    @Override
    @Scheduled(cron = "0 0 19 ? * SAT") // 결제일 알림
    public void scheduledPaymentNotificationTasks() {
        ExternalPaymentNotificationDTO userEmailsDTO = communicationService.getUserEmails();
        if (userEmailsDTO != null) {
            communicationService.saveToExternalPaymentInfoEntity(userEmailsDTO);
        }
        schedulePaymentNotification();
    }
    @Override
    public void schedulePaymentNotification() {
        LocalDateTime currentDate = LocalDateTime.now();

        List<ExternalPaymentInfoEntity> pendingNotifications = externalPaymentInfoRepository.findByProcessedFalseAndDate(LocalDate.now());

        for (ExternalPaymentInfoEntity paymentInfo : pendingNotifications) {
            String email = paymentInfo.getEmail();
            // 새로운 알림 생성
            PaymentNotificationDTO newNotification = PaymentNotificationDTO.builder()
                    .email(email)
                    .notificationContent("결제일이 얼마 남지 않았습니다.")
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
    @Scheduled(cron = "0 0 1 * * *")
    public void scheduledSurveyNotifications() {
        LocalDateTime currentDate = LocalDateTime.now();
        // 포맷 정의: MM-dd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일");
        String formattedCurrentDate = currentDate.format(formatter);

        // 내용을 결합
        String combinedContent = formattedCurrentDate + " 설문조사에 참여해주세요~";

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