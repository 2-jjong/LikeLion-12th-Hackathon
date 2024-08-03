package com.example.notificationserver.Service;

import com.example.notificationserver.DTO.ExternalDietNotificationDTO;
import com.example.notificationserver.DTO.ExternalPaymentApiResponseDTO;
import com.example.notificationserver.DTO.ExternalPaymentNotificationDTO;
import com.example.notificationserver.Entity.ExternalPaymentInfoEntity;
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

    @Autowired
    public CommunicationServiceImpl(DiscoveryClient discoveryClient, RestTemplate restTemplate, ExternalPaymentInfoRepository externalPaymentInfoRepository) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
        this.externalPaymentInfoRepository = externalPaymentInfoRepository;
    }

    @Override
    public String fetchAndSavePaymentInfo(String email) throws URISyntaxException {
        return null;
    }

    @Override
    public ExternalPaymentNotificationDTO getUserEmails() {
        // Eureka에서 PAYMENT-SERVER 인스턴스를 검색
        List<ServiceInstance> instances = discoveryClient.getInstances("PAYMENT-SERVER");
        if (instances.isEmpty()) {
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
        ResponseEntity<ExternalPaymentApiResponseDTO> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, ExternalPaymentApiResponseDTO.class);

        // 응답 처리(reponse가 200이면 성공)
        if (response.getStatusCode().is2xxSuccessful()) {
            ExternalPaymentApiResponseDTO userDTO = response.getBody();
            if (userDTO != null) {
                return new ExternalPaymentNotificationDTO(userDTO.getEmails(), false, LocalDate.now()); // 필드 이름을 emails로 수정
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
    public ExternalDietNotificationDTO getUserDiet() {
        // Eureka에서 PAYMENT-SERVER 인스턴스를 검색
        List<ServiceInstance> instances = discoveryClient.getInstances("FOOD-SERVER");
        if (instances.isEmpty()) {
            throw new IllegalStateException("No FOOD-SERVER instances available");
        }
        ServiceInstance userService = instances.get(0);

        // 요청 URI 설정
        URI uri = URI.create(userService.getUri() + "/daily/read/all");

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 엔티티 생성
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        // REST 요청 보내기 (URI, HTTP 메소드 타입, httpEntity, 반환 타입)
        ResponseEntity<ExternalDietNotificationDTO> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, ExternalDietNotificationDTO.class);

        // 응답 처리(reponse가 200이면 성공)
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new IllegalStateException("Failed to fetch emails from USER-SERVER");
        }
    }
}