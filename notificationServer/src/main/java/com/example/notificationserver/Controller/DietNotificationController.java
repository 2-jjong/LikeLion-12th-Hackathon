package com.example.notificationserver.Controller;

import com.example.notificationserver.DTO.DietNotificationDTO;
import com.example.notificationserver.Service.DietNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification/diet")
public class DietNotificationController {
    private final DietNotificationService DietNotificationService;

    @Autowired
    public DietNotificationController(DietNotificationService dietNotificationService) {
        this.DietNotificationService = dietNotificationService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<DietNotificationDTO> createDietNotification(@RequestBody DietNotificationDTO dietNotificationDTO) {
        DietNotificationDTO createdDietNotification = DietNotificationService.createDietNotification(dietNotificationDTO);
        return ResponseEntity.ok(createdDietNotification);
    }

    @CrossOrigin(origins = "http://192.168.61.42:3000")
    @GetMapping("/{id}")
    public ResponseEntity<DietNotificationDTO> getDietNotificationById(@PathVariable Long id) {
        DietNotificationDTO dietNotificationDTO = DietNotificationService.getDietNotificationById(id);
        return ResponseEntity.ok(dietNotificationDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DietNotificationDTO>> getDietNotificationsByUserId(@PathVariable Long userId) {
        List<DietNotificationDTO> dietNotificationDTOS = DietNotificationService.getDietNotificationsByUserId(userId);
        return ResponseEntity.ok(dietNotificationDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DietNotificationDTO> updateDietNotification(@PathVariable Long id, @RequestBody DietNotificationDTO dietNotificationDTO) {
        dietNotificationDTO.setId(id);
        DietNotificationDTO updatedDietNotification = DietNotificationService.updateDietNotification(dietNotificationDTO);
        return ResponseEntity.ok(updatedDietNotification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDietNotification(@PathVariable Long id) {
        DietNotificationService.deleteDietNotification(id);
        return ResponseEntity.noContent().build();
    }

    //@GetMapping("/user")
}
