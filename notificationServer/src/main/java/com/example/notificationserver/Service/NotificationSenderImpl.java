package com.example.notificationserver.Service;

import com.example.notificationserver.DTO.ExternalDietApiResponseDTO;
import com.example.notificationserver.DTO.ExternalDietNotificationDTO;
import com.example.notificationserver.DTO.ExternalPaymentApiResponseDTO;
import com.example.notificationserver.DTO.ExternalPaymentNotificationDTO;
import com.example.notificationserver.WebSocket.Handler.NotificationWebSocketHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;
import java.util.List;

@Component
public class NotificationSenderImpl implements NotificationSender{

    private final RestTemplate restTemplate;

    public NotificationSenderImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    @Override
//    public void sendDietNotificationNotification(ExternalDietNotificationDTO externalDTO) {
//        String message = externalDTO.getDiet();
//        List<WebSocketSession> sessions = NotificationWebSocketHandler.getSessionsByEmail(externalDTO.getEmail());
//
//        for (WebSocketSession session : sessions) {
//            try {
//                session.sendMessage(new TextMessage(message));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public void sendPaymentNotificationNotification(ExternalPaymentNotificationDTO externalDTO) {
//        String message = externalDTO.getEmail();
//        List<WebSocketSession> sessions = NotificationWebSocketHandler.getSessionsByEmail(externalDTO.getEmail());
//
//        for (WebSocketSession session : sessions) {
//            try {
//                session.sendMessage(new TextMessage(message));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public ExternalPaymentNotificationDTO fetchPaymentNotificationNotification(String url) {
//        ExternalPaymentApiResponseDTO response = restTemplate.getForObject(url, ExternalPaymentApiResponseDTO.class);
//
//        //필요한 데이터만 추출하여 ExternalDietNotificationDTO에 저장
//        if (response != null) {
//            return new ExternalPaymentNotificationDTO(response.getEmail(), response.getLastPaymentDate());
//        } else {
//            return null;
//        }
//    }
}
