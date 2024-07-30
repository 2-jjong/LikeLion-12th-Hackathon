package com.example.notificationserver.DTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DietNotificationDTO {
    private Long id;
    private Long userId;
    private String notificationContent;
    private LocalDateTime notificationTime;

    @Builder
    public DietNotificationDTO(Long id, Long userId, String notificationContent, LocalDateTime notificationTime){
        this.id = id;
        this.userId = userId;
        this.notificationContent = notificationContent;
        this.notificationTime = notificationTime;
    }
}