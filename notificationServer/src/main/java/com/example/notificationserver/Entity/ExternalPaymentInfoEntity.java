package com.example.notificationserver.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_payment")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExternalPaymentInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String email;
    @Column
    private LocalDateTime lastPaymentDate;

    @Builder
    public ExternalPaymentInfoEntity(String email, LocalDateTime lastPaymentDate){
        this.email = email;
        this.lastPaymentDate = lastPaymentDate;
    }
}
