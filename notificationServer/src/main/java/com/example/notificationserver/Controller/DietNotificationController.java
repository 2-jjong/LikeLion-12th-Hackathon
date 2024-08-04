package com.example.notificationserver.Controller;

import com.example.notificationserver.DTO.DietNotificationDTO;
import com.example.notificationserver.Service.DietNotificationService;
import com.example.notificationserver.Service.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification/diet")
public class DietNotificationController {
    private final DietNotificationService dietNotificationService;
    private final NotificationServiceImpl notificationServiceImpl;

    @Autowired
    public DietNotificationController(DietNotificationService dietNotificationService, NotificationServiceImpl notificationServiceImpl) {
        this.dietNotificationService = dietNotificationService;
        this.notificationServiceImpl = notificationServiceImpl;
    }

    @PostMapping("/create")
    public ResponseEntity<DietNotificationDTO> createDietNotification(@RequestBody DietNotificationDTO dietNotificationDTO) {
        DietNotificationDTO createdDietNotification = dietNotificationService.createDietNotification(dietNotificationDTO);
        notificationServiceImpl.sendNotification(createdDietNotification); // 알림 전송
        return ResponseEntity.ok(createdDietNotification);
    }

    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping("/{id}")
    public ResponseEntity<DietNotificationDTO> getDietNotificationById(@PathVariable Long id) {
        DietNotificationDTO dietNotificationDTO = dietNotificationService.getDietNotificationById(id);
        return ResponseEntity.ok(dietNotificationDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DietNotificationDTO> updateDietNotification(@PathVariable Long id, @RequestBody DietNotificationDTO dietNotificationDTO) {
        dietNotificationDTO.setId(id);
        DietNotificationDTO updatedDietNotification = dietNotificationService.updateDietNotification(dietNotificationDTO);
        notificationServiceImpl.sendNotification(updatedDietNotification); // 알림 전송
        return ResponseEntity.ok(updatedDietNotification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDietNotification(@PathVariable Long id) {
        dietNotificationService.deleteDietNotification(id);
        return ResponseEntity.noContent().build();
    }
}