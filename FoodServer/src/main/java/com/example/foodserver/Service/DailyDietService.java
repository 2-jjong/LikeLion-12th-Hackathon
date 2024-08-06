package com.example.foodserver.Service;

import com.example.foodserver.DTO.Response.DailyDietDTO;
import com.example.foodserver.DTO.Request.DailyDietRequestDTO;
import com.example.foodserver.Entity.DailyDietEntity;

import java.time.LocalDate;
import java.util.List;

public interface DailyDietService {
    List<DailyDietDTO> getByUserEmailAndDate(String userEmail, LocalDate date);
    List<DailyDietDTO> getByDate(LocalDate date);
    List<DailyDietEntity> convertToDailyDietEntities(List<DailyDietRequestDTO> dailyDietDTOS, String userEmail);
    List<DailyDietDTO> convertToDailyDietDTOS(List<DailyDietEntity> dailyDietEntities);
}