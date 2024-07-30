package com.example.notificationserver.DTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NotificationTypeDTO {
    private Long id;
    private String notificationContent;

    @Builder
    public NotificationTypeDTO(Long id, String notificationContent){
        this.id = id;
        this.notificationContent = notificationContent;
    }
}