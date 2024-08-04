package com.example.notificationserver.Service;

import com.example.notificationserver.DTO.ExternalDietNotificationDTO;
import com.example.notificationserver.DTO.ExternalPaymentNotificationDTO;

import java.net.URISyntaxException;

public interface CommunicationService {
    public String fetchAndSavePaymentInfo(String email) throws URISyntaxException;
    ExternalPaymentNotificationDTO getUserEmails();
    void saveToExternalPaymentInfoEntity(ExternalPaymentNotificationDTO dto);
    void getUserDietEmails(String date);
}
