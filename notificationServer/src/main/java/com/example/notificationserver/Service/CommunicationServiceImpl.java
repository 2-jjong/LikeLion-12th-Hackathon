package com.example.notificationserver.Service;

import com.example.notificationserver.DTO.*;
import com.example.notificationserver.Entity.ExternalDietInfoEntity;
import com.example.notificationserver.Entity.ExternalPaymentInfoEntity;
import com.example.notificationserver.Repository.ExternalDietInfoRepository;
import com.example.notificationserver.Repository.ExternalPaymentInfoRepository;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders; // 올바른 HttpHeaders 임포트
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;


@Service
public class CommunicationServiceImpl implements CommunicationService{

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;
    private final ExternalPaymentInfoRepository externalPaymentInfoRepository;
    private final ExternalDietInfoRepository externalDietInfoRepository;

    @Autowired
    public CommunicationServiceImpl(DiscoveryClient discoveryClient, RestTemplate restTemplate, ExternalPaymentInfoRepository externalPaymentInfoRepository, ExternalDietInfoRepository externalDietInfoRepository) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
        this.externalPaymentInfoRepository = externalPaymentInfoRepository;
        this.externalDietInfoRepository = externalDietInfoRepository;
    }

    @Override
    public String fetchAndSavePaymentInfo(String email) throws URISyntaxException {
        return null;
    }

    @Override
    public ExternalPaymentNotificationDTO getUserEmails() {
        // Eureka에서 PAYMENT-SERVER 인스턴스를 검색
        List<ServiceInstance> instances = discoveryClient.getInstances("PAYMENT-SERVER");
        if (instances == null || instances.isEmpty()) {
            throw new IllegalStateException("No PAYMENT-SERVER instances available");
        }
        ServiceInstance paymentService = instances.get(0);

        // 요청 URI 설정
        URI uri = URI.create(paymentService.getUri() + "/api/payment/unpaid-users");

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 엔티티 생성
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        // REST 요청 보내기 (URI, HTTP 메소드 타입, httpEntity, 반환 타입)
        ResponseEntity<ExternalPaymentApiResponseDTO> response;
        try {
            response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, ExternalPaymentApiResponseDTO.class);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to send request to PAYMENT-SERVER", e);
        }

        // 응답 처리(response가 200이면 성공)
        if (response.getStatusCode().is2xxSuccessful()) {
            ExternalPaymentApiResponseDTO userDTO = response.getBody();
            if (userDTO != null && userDTO.getEmail() != null) {
                // 응답 데이터 출력
                System.out.println("Received response from PAYMENT-SERVER: " + userDTO.getEmail());
                return new ExternalPaymentNotificationDTO(userDTO.getEmail(), false, LocalDate.now());
            }
        } else {
            throw new IllegalStateException("Failed to fetch emails from PAYMENT-SERVER");
        }
        return null;
    }


    @Override
    public void saveToExternalPaymentInfoEntity(ExternalPaymentNotificationDTO dto) {
        List<String> emails = dto.getEmails();
        for (String email : emails) {
            ExternalPaymentInfoEntity entity = ExternalPaymentInfoEntity.builder()
                    .email(email)
                    .processed(dto.isProcessed())
                    .date(dto.getDate())
                    .build();
            externalPaymentInfoRepository.save(entity);
        }
    }

    @Override
    public void getUserDietEmails(String date) {
        // Eureka에서 USERMEAL-SERVER 인스턴스를 검색
        List<ServiceInstance> instances = discoveryClient.getInstances("USERMEAL-SERVER");
        if (instances == null || instances.isEmpty()) {
            throw new IllegalStateException("No USERMEAL-SERVER instances available");
        }
        ServiceInstance userMealService = instances.get(0);

        System.out.println(userMealService.getUri() + "/api/userMeal/daily/read/" + date);
        // 요청 URI 설정
        URI uri = URI.create(userMealService.getUri() + "/api/userMeal/daily/read/" + date);

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 엔티티 생성
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        // REST 요청 보내기 (URI, HTTP 메소드 타입, httpEntity, 반환 타입)
        ResponseEntity<DailyDietDTO[]> response;
        try {
            response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, DailyDietDTO[].class);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to send request to USERMEAL-SERVER", e);
        }

        // 응답 처리(response가 200이면 성공)
        if (response.getStatusCode().is2xxSuccessful()) {
            DailyDietDTO[] dailyDiets = response.getBody();
            if (dailyDiets != null) {
                for (DailyDietDTO dailyDiet : dailyDiets) {
                    // 응답 데이터 출력
                    System.out.println("Received response from USERMEAL-SERVER: " + dailyDiet);

                    // 각 사용자에 대해 아침, 점심, 저녁 메뉴 이름 추출
                    String breakfast = null;
                    String lunch = null;
                    String dinner = null;
                    for (MealSelectionDTO mealSelection : dailyDiet.getMealSelections()) {
                        if ("아침".equals(mealSelection.getMealTime())) {
                            breakfast = mealSelection.getFoodMenu().getName();
                        } else if ("점심".equals(mealSelection.getMealTime())) {
                            lunch = mealSelection.getFoodMenu().getName();
                        } else if ("저녁".equals(mealSelection.getMealTime())) {
                            dinner = mealSelection.getFoodMenu().getName();
                        }
                    }

                    // DTO를 엔티티로 변환
                    ExternalDietInfoEntity entity = ExternalDietInfoEntity.builder()
                            .email(dailyDiet.getUserEmail())
                            .breakfast(breakfast)
                            .lunch(lunch)
                            .dinner(dinner)
                            .processedBreakfast(false)
                            .processedLunch(false)
                            .processedDinner(false)
                            .date(dailyDiet.getDate())
                            .build();

                    // 엔티티 저장
                    externalDietInfoRepository.save(entity);
                }
            }
        } else {
            throw new IllegalStateException("Failed to fetch data from USERMEAL-SERVER");
        }
    }
}