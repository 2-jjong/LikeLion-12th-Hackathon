package com.example.notificationserver.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ExternalPaymentNotificationDTO {
    private String email;
    private LocalDateTime lastPaymentDate;

    @Builder
    public ExternalPaymentNotificationDTO(String email, LocalDateTime lastPaymentDate){
        this.email = email;
        this.lastPaymentDate = lastPaymentDate;
    }
}
