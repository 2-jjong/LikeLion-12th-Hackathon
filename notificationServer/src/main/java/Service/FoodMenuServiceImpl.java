package Service;

import DAO.FoodMenuDAO;
import DTO.FoodMenuDTO;
import WebSocket.Handler.NotificationWebSocketHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Service
public class FoodMenuServiceImpl implements FoodMenuService {

    private final FoodMenuDAO foodMenuDAO;
    private final RestTemplate restTemplate;

    public FoodMenuServiceImpl(FoodMenuDAO foodMenuDAO, RestTemplate restTemplate) {
        this.foodMenuDAO = foodMenuDAO;
        this.restTemplate = restTemplate;
    }

    @Override
    public FoodMenuDTO createFoodMenu(FoodMenuDTO foodMenuDTO) {
        return foodMenuDAO.create(foodMenuDTO);
    }

    @Override
    public FoodMenuDTO getFoodMenuById(Long id) {
        return foodMenuDAO.findById(id);
    }

    @Override
    public List<FoodMenuDTO> getFoodMenusByUserId(Long userId) {
        return foodMenuDAO.findByUserId(userId);
    }

    @Override
    public FoodMenuDTO updateFoodMenu(FoodMenuDTO foodMenuDTO) {
        return foodMenuDAO.update(foodMenuDTO);
    }

    @Override
    public void deleteFoodMenu(Long id) {
        foodMenuDAO.delete(id);
    }

    //자동 저장
    @Scheduled(cron = "0 0 7")// 이게 7시 발송이라던데
    public void fetchAndSendFoodMenuNotification(){
        String url = "http://diet/user/diets"; //예시임
        FoodMenuDTO foodMenuDTO = restTemplate.getForObject(url, FoodMenuDTO.class);
    }
    //알림 보내기
    @Override
    public void sendFoodMenuNotification(FoodMenuDTO foodMenuDTO) {
        String message = foodMenuDTO.getNotificationContent();
        List<WebSocketSession> sessions = NotificationWebSocketHandler.getSessionsByUserId(foodMenuDTO.getUserId());

        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
