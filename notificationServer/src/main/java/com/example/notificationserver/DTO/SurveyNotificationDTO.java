package com.example.notificationserver.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SurveyNotificationDTO {
    private String email;
    private String notificationContent;
    private LocalDateTime notificationTime;

    @Builder
    public SurveyNotificationDTO(String email, String notificationContent, LocalDateTime notificationTime) {
        this.email = email;
        this.notificationContent = notificationContent;
        this.notificationTime = notificationTime;
    }
}
