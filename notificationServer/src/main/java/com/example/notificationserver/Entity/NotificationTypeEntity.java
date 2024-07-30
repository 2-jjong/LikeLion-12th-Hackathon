package com.example.notificationserver.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notification_type")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class NotificationTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String notificationContent;

    @Builder
    public NotificationTypeEntity(String notificationContent){
        this.notificationContent = notificationContent;
    }
}
