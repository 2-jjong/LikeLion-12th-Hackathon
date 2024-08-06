package com.example.foodserver.Service;

import com.example.foodserver.DAO.WeeklyDietDAO;
import com.example.foodserver.DTO.Request.UserDailyMealPlanDTO;
import com.example.foodserver.DTO.Response.WeeklyDietDTO;
import com.example.foodserver.DTO.Request.WeeklyDietRequestDTO;
import com.example.foodserver.Entity.DailyDietEntity;
import com.example.foodserver.Entity.MealSelectionEntity;
import com.example.foodserver.Entity.WeeklyDietEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeeklyDietServiceImpl implements WeeklyDietService {

    private final WeeklyDietDAO weeklyDietDAO;
    private final DailyDietService dailyDietService;
    private final CommunicationService communicationService;

    @Autowired
    public WeeklyDietServiceImpl(WeeklyDietDAO weeklyDietDAO,
                                 DailyDietService dailyDietService,
                                 CommunicationService communicationService) {
        this.weeklyDietDAO = weeklyDietDAO;
        this.dailyDietService = dailyDietService;
        this.communicationService = communicationService;
    }

    @Override
    @Transactional
    public WeeklyDietDTO createWeeklyDiet(WeeklyDietRequestDTO weeklyDietDTO) {
        if(weeklyDietDAO.existsByCurrentWeeklyMealPlan(weeklyDietDTO.getStartDate(), weeklyDietDTO.getUserEmail())){
            return null;
        }
        WeeklyDietEntity entity = convertToWeeklyDietEntity(weeklyDietDTO);
        weeklyDietDAO.create(entity);
        communicationService.createReview(entity.getUserEmail(), convertToDailyMealPlanDTOS(entity));
        return convertToWeeklyDietDTO(entity);
    }

    @Override
    public WeeklyDietDTO getWeeklyDietByUserEmail(String userEmail) {
        return convertToWeeklyDietDTO(weeklyDietDAO.getByUserEmail(userEmail));
    }

    @Override
    public WeeklyDietDTO getWeeklyDietByWeeklyId(Long weeklyId){
        return convertToWeeklyDietDTO(weeklyDietDAO.getByWeeklyId(weeklyId));
    }

    public WeeklyDietEntity convertToWeeklyDietEntity(WeeklyDietRequestDTO weeklyDietDTO) {
        return WeeklyDietEntity.builder()
                .userEmail(weeklyDietDTO.getUserEmail())
                .startDate(weeklyDietDTO.getStartDate())
                .endDate(weeklyDietDTO.getEndDate())
                .dailyMealPlans(dailyDietService.convertToDailyDietEntities(weeklyDietDTO.getDailyMealPlans(), weeklyDietDTO.getUserEmail()))
                .build();
    }

    public WeeklyDietDTO convertToWeeklyDietDTO(WeeklyDietEntity weeklyDietEntity) {
        return WeeklyDietDTO.builder()
                .weeklyId(weeklyDietEntity.getWeeklyId())
                .userEmail(weeklyDietEntity.getUserEmail())
                .startDate(weeklyDietEntity.getStartDate())
                .endDate(weeklyDietEntity.getEndDate())
                .dailyMealPlans(dailyDietService.convertToDailyDietDTOS(weeklyDietEntity.getDailyMealPlans()))
                .build();
    }

    public List<Long> convertToFoodMenuIds(DailyDietEntity dailyDietEntity){
        List<Long> foodMenuIds = new ArrayList<>();
        for(MealSelectionEntity mealSelectionEntity : dailyDietEntity.getMealOptions()) {
            foodMenuIds.add(mealSelectionEntity.getFoodMenuId());
        }
        return foodMenuIds;
    }

    public UserDailyMealPlanDTO convertToDailyMealPlanDTO(DailyDietEntity dailyDietEntity) {
        return UserDailyMealPlanDTO.builder()
                .date(dailyDietEntity.getDay())
                .foodMenuIds(convertToFoodMenuIds(dailyDietEntity))
                .build();
    }

    public List<UserDailyMealPlanDTO> convertToDailyMealPlanDTOS(WeeklyDietEntity weeklyDietEntity) {
        List<UserDailyMealPlanDTO> userDailyMealPlanDTOS = new ArrayList<>();
        for(DailyDietEntity dailyDietEntity : weeklyDietEntity.getDailyMealPlans()) {
            userDailyMealPlanDTOS.add(convertToDailyMealPlanDTO(dailyDietEntity));
        }
        return userDailyMealPlanDTOS;
    }
}