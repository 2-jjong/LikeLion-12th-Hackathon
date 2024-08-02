package com.example.notificationserver.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "diet_notification")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DietNotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long userId;
    @Column(name = "email")
    private String email;
    @Column
    private String notificationContent;
    @Column
    private LocalDateTime notificationTime;

    @Builder
    public DietNotificationEntity(Long userId, String email, String notificationContent, LocalDateTime notificationTime){
        this.userId = userId;
        this.email = email;
        this.notificationContent = notificationContent;
        this.notificationTime = notificationTime;
    }


    public void updateUserId(Long userId){
        this.userId = userId;
    }

    public void updateNotificationContent(String notificationContent){
        this.notificationContent = notificationContent;
    }

    public void updateNotificationTime(LocalDateTime notificationTime){
        this.notificationTime = notificationTime;
    }


}
