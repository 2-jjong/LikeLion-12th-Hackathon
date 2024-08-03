package com.example.notificationserver.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExternalDietNotificationDTO {
    private String email;
    private String diet;


    @Builder
    public ExternalDietNotificationDTO(String email, String diet){
        this.email = email;
        this.diet = diet;
    }
}
