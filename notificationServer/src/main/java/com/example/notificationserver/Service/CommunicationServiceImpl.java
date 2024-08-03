package com.example.notificationserver.Service;

import com.example.notificationserver.DTO.ExternalPaymentNotificationDTO;
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
import java.util.List;


@Service
public class CommunicationServiceImpl implements CommunicationService{

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    @Autowired
    public CommunicationServiceImpl(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    @Override
    public String fetchAndSavePaymentInfo(String email) throws URISyntaxException {
        return null;
    }

    public ExternalPaymentNotificationDTO getUserEmails() {
        // Eureka에서 PAYMENT-SERVER 인스턴스를 검색
        List<ServiceInstance> instances = discoveryClient.getInstances("PAYMENT-SERVER");
        if (instances.isEmpty()) {
            throw new IllegalStateException("No PAYMENT-SERVER instances available");
        }
        ServiceInstance userService = instances.get(0);

        // 요청 URI 설정
        URI uri = URI.create(userService.getUri() + "/users/emails");

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 엔티티 생성
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        // REST 요청 보내기 (URI, HTTP 메소드 타입, httpEntity, 반환 타입)
        ResponseEntity<ExternalPaymentNotificationDTO> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, ExternalPaymentNotificationDTO.class);

        // 응답 처리(reponse가 200이면 성공)
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new IllegalStateException("Failed to fetch emails from USER-SERVER");
        }
    }
}