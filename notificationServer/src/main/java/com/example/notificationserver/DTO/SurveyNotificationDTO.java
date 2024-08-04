package com.example.notificationserver.DTO;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SurveyNotificationDTO {
    private String email;
    private String notificationContent;
    private LocalDateTime notificationTime;
    private Long dailyReviewId;
    private LocalDate reviewDate; // 변경된 부분
    private List<ReviewDTO> reviews;
}
