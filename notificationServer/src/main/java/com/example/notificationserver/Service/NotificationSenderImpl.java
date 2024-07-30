package com.example.notificationserver.Service;

import com.example.notificationserver.DTO.ExternalDietApiResponseDTO;
import com.example.notificationserver.DTO.ExternalDietNotificationDTO;
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

    //타 서버 url
    @Override
    public ExternalDietNotificationDTO fetchDietNotificationNotification(String url) {
        // REST API를 통해 데이터 가져오기
        ExternalDietApiResponseDTO response = restTemplate.getForObject(url, ExternalDietApiResponseDTO.class);

        // 필요한 데이터만 추출하여 ExternalDietNotificationDTO에 저장
        if (response != null) {
            return new ExternalDietNotificationDTO(response.getUserId(), response.getDiet(), response.getContent());
        } else {
            return null;
        }
    }

    @Override
    public void sendDietNotificationNotification(ExternalDietNotificationDTO externalDTO) {
        String message = externalDTO.getNotificationContent();
        List<WebSocketSession> sessions = NotificationWebSocketHandler.getSessionsByUserId(externalDTO.getUserId());

        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
