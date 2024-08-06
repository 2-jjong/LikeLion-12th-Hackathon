package com.example.foodserver.DTO.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MealSelectionRequestDTO {
    private Long mealSelectionId;
    private String mealType;
    private FoodMenuDTO foodMenus;
    private int count;
}