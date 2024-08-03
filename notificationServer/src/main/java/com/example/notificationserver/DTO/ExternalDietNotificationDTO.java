package com.example.notificationserver.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ExternalDietNotificationDTO {
    private String email;
    private String diet;
    private String notificationContent;

    @Builder
    public ExternalDietNotificationDTO(String email, String diet, String notificationContent){
        this.email = email;
        this.diet = diet;
        this.notificationContent = notificationContent;
    }
}
