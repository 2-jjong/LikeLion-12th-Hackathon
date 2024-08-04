package com.example.notificationserver.Scheduler;

import com.example.notificationserver.DTO.ReviewDTO;
import com.example.notificationserver.DTO.SurveyNotificationDTO;
import com.example.notificationserver.Repository.DailyReviewRepository;
import com.example.notificationserver.Service.CommunicationService;
import com.example.notificationserver.Service.NotificationService;
import com.example.notificationserver.Service.SurveyNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SurveyNotificationScheduler {

    private final CommunicationService communicationService;
    private final SurveyNotificationService surveyNotificationService;
    private final NotificationService notificationService;
    private final DailyReviewRepository dailyReviewRepository;

    @Autowired
    public SurveyNotificationScheduler(CommunicationService communicationService, SurveyNotificationService surveyNotificationService, NotificationService notificationService, DailyReviewRepository dailyReviewRepository) {
        this.communicationService = communicationService;
        this.surveyNotificationService = surveyNotificationService;
        this.notificationService = notificationService;
        this.dailyReviewRepository = dailyReviewRepository;
    }

    //@Scheduled(cron = "0/30 * * * * ?") // 매일 오전 6시에 실행
    //@Transactional
    public void scheduledReviewFetch() {
        communicationService.fetchAndSaveDailyReviews();
        //scheduledSurveyNotifications();
    }

//    @Transactional
//    public void scheduledSurveyNotifications() {
//        List<DailyReviewEntity> dailyReviews = dailyReviewRepository.findAll();
//
//        // Lazy loading을 위해 리뷰들을 초기화합니다.
//        for (DailyReviewEntity dailyReview : dailyReviews) {
//            dailyReview.getReviews().size();
//        }
//
//        LocalDateTime currentDate = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일");
//        String formattedCurrentDate = currentDate.format(formatter);
//
//        Map<String, List<DailyReviewEntity>> groupedReviews = dailyReviews.stream()
//                .collect(Collectors.groupingBy(DailyReviewEntity::getUserEmail));
//
//        for (Map.Entry<String, List<DailyReviewEntity>> entry : groupedReviews.entrySet()) {
//            String email = entry.getKey();
//            List<DailyReviewEntity> reviews = entry.getValue();
//
//            String combinedContent = formattedCurrentDate + " 설문조사에 참여해주세요~";
//
//            for (DailyReviewEntity dailyReview : reviews) {
//                List<ReviewDTO> reviewDTOs = dailyReview.getReviews().stream()
//                        .map(review -> new ReviewDTO(
//                                review.getReviewId(),
//                                review.getFoodImage(),
//                                review.getFoodName(),
//                                review.getLikes(),
//                                review.getDislikes(),
//                                review.getComments()
//                        ))
//                        .collect(Collectors.toList());
//
//                SurveyNotificationDTO notification = SurveyNotificationDTO.builder()
//                        .email(email)
//                        .notificationContent(combinedContent)
//                        .notificationTime(currentDate)
//                        .dailyReviewId(dailyReview.getDailyReviewId())
//                        .reviewDate(dailyReview.getReviewDate())
//                        .reviews(reviewDTOs)
//                        .build();
//
//                surveyNotificationService.createSurveyNotification(notification);
//                notificationService.sendSurveyNotification(notification);
//            }
//        }
//
//        // 리뷰 및 데일리 리뷰 삭제
//        dailyReviewRepository.deleteAll();
//    }
}
