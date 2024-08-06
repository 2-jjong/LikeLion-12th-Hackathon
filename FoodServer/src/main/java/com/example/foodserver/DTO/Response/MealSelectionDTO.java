package com.example.foodserver.DTO.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MealSelectionDTO {
    private Long mealSelectionId;
    private String mealType;
    private FoodMenuDTO foodMenu;
    private int count;
}