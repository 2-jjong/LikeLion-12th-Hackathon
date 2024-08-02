package com.example.notificationserver.Service;

import com.example.notificationserver.DTO.SurveyNotificationDTO;

public interface SurveyNotificationService {
    SurveyNotificationDTO createSurveyNotification(SurveyNotificationDTO surveyNotificationDTO);
    SurveyNotificationDTO updateSurveyNotification(SurveyNotificationDTO surveyNotificationDTO);
    SurveyNotificationDTO deleteSurveyNotification(SurveyNotificationDTO surveyNotificationDTO);
}
