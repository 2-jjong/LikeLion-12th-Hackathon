package WebSocket.Handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationWebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<Long, List<WebSocketSession>> userSessions = new ConcurrentHashMap<>();

    //밑에꺼 자동완성이라 뭐 어케써야하는거냐? 하나도 모르겠네
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        // Handle the received message
        System.out.println("Received: " + payload);

        // Echo the received message back to the client
        session.sendMessage(new TextMessage("Echo: " + payload));
    }
    //먼 개소리징
    public static List<WebSocketSession> getSessionsByUserId(Long userId) {
        return userSessions.getOrDefault(userId, new ArrayList<>());
    }


}
