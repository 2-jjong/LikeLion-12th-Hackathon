package com.example.notificationserver.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class DailyReviewDTO {
    private Long dailyReviewId;
    private String userEmail;
    private LocalDate reviewDate;
    private List<ReviewDTO> reviews;

    @Data
    @Builder
    public static class ReviewDTO {
        private Long reviewId;
        private String foodImage;
        private String foodName;
        private Long likes;
        private Long disLikes;
        private List<String> comments;
    }
}

