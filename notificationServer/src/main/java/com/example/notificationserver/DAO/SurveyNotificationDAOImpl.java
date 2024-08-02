package com.example.notificationserver.DAO;

import com.example.notificationserver.DTO.SurveyNotificationDTO;
import com.example.notificationserver.Entity.SurveyNotificationEntity;
import com.example.notificationserver.Repository.SurveyNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SurveyNotificationDAOImpl implements SurveyNotificationDAO{
    private final SurveyNotificationRepository surveyNotificationRepository;

    @Autowired
    public SurveyNotificationDAOImpl(SurveyNotificationRepository surveyNotificationRepository) {
        this.surveyNotificationRepository = surveyNotificationRepository;
    }

    //Entity to DTO
    private SurveyNotificationDTO surveyNotificationEntityToDTO(SurveyNotificationEntity surveyNotificationEntity) {
        return SurveyNotificationDTO.builder()
                .email(surveyNotificationEntity.getEmail())
                .notificationContent(surveyNotificationEntity.getNotificationContent())
                .notificationTime(surveyNotificationEntity.getNotificationTime())
                .build();
    }

    //DTO to Entity
    private SurveyNotificationEntity DTOtoSurveyNotificationEntity(SurveyNotificationDTO surveyNotificationDTO) {
        return SurveyNotificationEntity.builder()
                .email(surveyNotificationDTO.getEmail())
                .notificationContent(surveyNotificationDTO.getNotificationContent())
                .notificationTime(surveyNotificationDTO.getNotificationTime())
                .build();
    }

    @Override
    public SurveyNotificationDTO create(SurveyNotificationDTO surveyNotificationDTO) {
        SurveyNotificationEntity notificationEntity = DTOtoSurveyNotificationEntity(surveyNotificationDTO);
        return surveyNotificationEntityToDTO(surveyNotificationRepository.save(notificationEntity));
    }

    @Override
    public SurveyNotificationDTO update(SurveyNotificationDTO surveyNotificationDTO) {
        SurveyNotificationEntity notificationEntity = DTOtoSurveyNotificationEntity(surveyNotificationDTO);
        return surveyNotificationEntityToDTO(surveyNotificationRepository.save(notificationEntity));
    }

    @Override
    public void delete(SurveyNotificationDTO surveyNotificationDTO) {
        SurveyNotificationEntity notificationEntity = DTOtoSurveyNotificationEntity(surveyNotificationDTO);
        surveyNotificationRepository.delete(notificationEntity);
    }

}
