package com.example.notificationserver.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "survey_notification")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SurveyNotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String email;
    @Column
    private String notificationContent;
    @Column
    private LocalDateTime notificationTime;

    @Builder
    public SurveyNotificationEntity(String email, String notificationContent, LocalDateTime notificationTime) {
        this.email = email;
        this.notificationContent = notificationContent;
        this.notificationTime = notificationTime;
    }


}
