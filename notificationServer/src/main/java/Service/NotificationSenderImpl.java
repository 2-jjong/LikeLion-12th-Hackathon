package Service;

import DTO.ExternalDietNotificationDTO;
import WebSocket.Handler.NotificationWebSocketHandler;
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

    @Override
    public ExternalDietNotificationDTO fetchDietNotificationNotification(String url) {
        return restTemplate.getForObject(url, ExternalDietNotificationDTO.class);
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
