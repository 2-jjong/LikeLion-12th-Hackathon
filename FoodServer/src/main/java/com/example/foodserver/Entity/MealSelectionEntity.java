package com.example.foodserver.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "meal_selection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealSelectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mealSelectionId;
    @Column
    private Long foodMenuId;
    @Column
    private String mealType;
    @Column
    private int count;
}