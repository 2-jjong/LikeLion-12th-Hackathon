package com.example.foodserver.DTO.Response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DailyDietDTO {
    private Long dailyDietId;
    private String userEmail;
    private LocalDate day;
    private List<MealSelectionDTO> mealOptions;
}