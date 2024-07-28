package Service;

import DTO.DietNotificationDTO;
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

    public DietNotificationDTO fetchFoodMenuNotification(String url) {
        return restTemplate.getForObject(url, DietNotificationDTO.class);
    }

    public void sendFoodMenuNotification(DietNotificationDTO dietNotificationDTO) {
        String message = dietNotificationDTO.getNotificationContent();
        List<WebSocketSession> sessions = NotificationWebSocketHandler.getSessionsByUserId(dietNotificationDTO.getUserId());

        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
