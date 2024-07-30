package com.example.notificationserver.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ExternalDietNotificationDTO {
    private Long userId;
    private String diet;
    private String notificationContent;

    @Builder
    public ExternalDietNotificationDTO(Long userId, String diet, String notificationContent){
        this.userId = userId;
        this.diet = diet;
        this.notificationContent = notificationContent;
    }
}
