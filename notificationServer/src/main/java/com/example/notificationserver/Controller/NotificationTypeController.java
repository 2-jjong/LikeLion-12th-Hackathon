package com.example.notificationserver.Controller;

import com.example.notificationserver.DTO.NotificationTypeDTO;
import com.example.notificationserver.Service.NotificationTypeService;
import com.example.notificationserver.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification_type")
public class NotificationTypeController {
    private final NotificationType,Service NotificationTypeService;

    @Autowired
    public NotificationTypeController(NotificationTypeService NotificationTypeService, NotificationService notificationService) {
        this.NotificationTypeService = NotificationTypeService;
    }

    @PostMapping("/create")
    public ResponseEntity<NotificationTypeDTO> createNotificationType(@RequestBody NotificationTypeDTO NotificationTypeDTO) {
        NotificationTypeDTO createdNotificationType = NotificationTypeService.createNotificationType(NotificationTypeDTO);
        return ResponseEntity.ok(createdNotificationType);
    }

    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping("/{id}")
    public ResponseEntity<NotificationTypeDTO> getNotificationTypeById(@PathVariable Long id) {
        NotificationTypeDTO NotificationTypeDTO = NotificationTypeService.getNotificationTypeById(id);
        return ResponseEntity.ok(NotificationTypeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationTypeDTO> updateNotificationType(@PathVariable Long id, @RequestBody NotificationTypeDTO NotificationTypeDTO) {
        NotificationTypeDTO.setId(id);
        NotificationTypeDTO updatedNotificationType = NotificationTypeService.updateNotificationType(NotificationTypeDTO);
        return ResponseEntity.ok(updatedNotificationType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificationType(@PathVariable Long id) {
        NotificationTypeService.deleteNotificationType(id);
        return ResponseEntity.noContent().build();
    }
}