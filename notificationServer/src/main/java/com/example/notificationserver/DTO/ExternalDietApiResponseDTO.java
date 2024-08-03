package com.example.notificationserver.DTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ExternalDietApiResponseDTO {
    private String email;
    private String diet;
    private String content;

    @Builder
    private ExternalDietApiResponseDTO(String email, String content) {
        this.email = email;
        this.content = content;
    }
}
