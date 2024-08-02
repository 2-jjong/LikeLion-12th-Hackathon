package com.example.notificationserver.DAO;

import com.example.notificationserver.DTO.SurveyNotificationDTO;

public interface SurveyNotificationDAO {
    SurveyNotificationDTO create(SurveyNotificationDTO surveyNotificationDTO);
    SurveyNotificationDTO update(SurveyNotificationDTO surveyNotificationDTO);
    void delete(SurveyNotificationDTO surveyNotificationDTO);
}
