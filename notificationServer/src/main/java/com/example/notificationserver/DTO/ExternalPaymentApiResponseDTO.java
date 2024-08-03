package com.example.notificationserver.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ExternalPaymentApiResponseDTO {
    private String email;
    private LocalDateTime lastPaymentDate;

    @Builder
    public ExternalPaymentApiResponseDTO(String email, LocalDateTime lastPaymentDate){
        this.email = email;
        this.lastPaymentDate = lastPaymentDate;
    }
}
