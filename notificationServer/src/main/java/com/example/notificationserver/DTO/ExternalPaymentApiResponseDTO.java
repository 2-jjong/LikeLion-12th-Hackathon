package com.example.notificationserver.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExternalPaymentApiResponseDTO {
    private List<String> emails;

    @Builder
    public ExternalPaymentApiResponseDTO(List<String> emails){
        this.emails = emails;
    }
}
