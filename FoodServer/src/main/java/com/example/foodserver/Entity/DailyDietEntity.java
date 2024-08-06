package com.example.foodserver.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "daily_diet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyDietEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long dailyDietId;
    @Column
    private LocalDate day;
    @Column
    private String userEmail;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<MealSelectionEntity> mealOptions;
}