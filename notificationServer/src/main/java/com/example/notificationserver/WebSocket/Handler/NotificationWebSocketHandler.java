package com.example.notificationserver.WebSocket.Handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationWebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<Long, List<WebSocketSession>> userSessions = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        System.out.println("Received: " + payload);

        // Echo the received message back to the client
        try {
            session.sendMessage(new TextMessage("Echo: " + payload));
        } catch (IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }

    public static List<WebSocketSession> getSessionsByUserId(Long userId) {
        return userSessions.getOrDefault(userId, new ArrayList<>());
    }

    public static List<WebSocketSession> getSessionsByEmail(String email) {
        return userSessions.getOrDefault(email, new ArrayList<>());
    }

    public static void addSession(Long userId, WebSocketSession session) {
        userSessions.computeIfAbsent(userId, k -> new ArrayList<>()).add(session);
    }

    public static void removeSession(Long userId, WebSocketSession session) {
        List<WebSocketSession> sessions = userSessions.get(userId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                userSessions.remove(userId);
            }
        }
    }
}
