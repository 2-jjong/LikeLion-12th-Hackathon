package com.example.notificationserver.DTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ExternalDietApiResponseDTO {
    private Long userId;
    private String diet;
    private String content;

    @Builder
    private ExternalDietApiResponseDTO(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }
}
