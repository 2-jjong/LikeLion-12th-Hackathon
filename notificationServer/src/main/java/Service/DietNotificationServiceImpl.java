package Service;

import DAO.DietNotificationDAO;
import DTO.DietNotificationDTO;
import WebSocket.Handler.NotificationWebSocketHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

@Service
public class DietNotificationServiceImpl implements DietNotificationService {

    private final DietNotificationDAO dietNotificationDAO;
    private final RestTemplate restTemplate;

    public DietNotificationServiceImpl(DietNotificationDAO dietNotificationDAO, RestTemplate restTemplate) {
        this.dietNotificationDAO = dietNotificationDAO;
        this.restTemplate = restTemplate;
    }

    @Override
    public DietNotificationDTO createFoodMenu(DietNotificationDTO dietNotificationDTO) {
        return dietNotificationDAO.create(dietNotificationDTO);
    }

    @Override
    public DietNotificationDTO getFoodMenuById(Long id) {
        return dietNotificationDAO.findById(id);
    }

    @Override
    public List<DietNotificationDTO> getFoodMenusByUserId(Long userId) {
        return dietNotificationDAO.findByUserId(userId);
    }

    @Override
    public DietNotificationDTO updateFoodMenu(DietNotificationDTO dietNotificationDTO) {
        return dietNotificationDAO.update(dietNotificationDTO);
    }

    @Override
    public void deleteFoodMenu(Long id) {
        dietNotificationDAO.delete(id);
    }

    //자동 저장
    @Scheduled(cron = "0 0 7")// 이게 7시 발송이라던데
    public void fetchAndSendFoodMenuNotification(){
        String url = "http://diet/user/diets"; //예시임
        DietNotificationDTO dietNotificationDTO = restTemplate.getForObject(url, DietNotificationDTO.class);
    }
    //알림 보내기
    @Override
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
