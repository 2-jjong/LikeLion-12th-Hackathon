package Controller;

import DTO.DietNotificationDTO;
import Service.DietNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification/food-menu")
public class DietNotificationController {
    private final DietNotificationService DietNotificationService;

    @Autowired
    public DietNotificationController(DietNotificationService dietNotificationService) {
        this.DietNotificationService = dietNotificationService;
    }

    @PostMapping
    public ResponseEntity<DietNotificationDTO> createFoodMenu(@RequestBody DietNotificationDTO dietNotificationDTO) {
        DietNotificationDTO createdFoodMenu = DietNotificationService.createFoodMenu(dietNotificationDTO);
        return ResponseEntity.ok(createdFoodMenu);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DietNotificationDTO> getFoodMenuById(@PathVariable Long id) {
        DietNotificationDTO dietNotificationDTO = DietNotificationService.getFoodMenuById(id);
        return ResponseEntity.ok(dietNotificationDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DietNotificationDTO>> getFoodMenusByUserId(@PathVariable Long userId) {
        List<DietNotificationDTO> dietNotificationDTOS = DietNotificationService.getFoodMenusByUserId(userId);
        return ResponseEntity.ok(dietNotificationDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DietNotificationDTO> updateFoodMenu(@PathVariable Long id, @RequestBody DietNotificationDTO dietNotificationDTO) {
        dietNotificationDTO.setId(id);
        DietNotificationDTO updatedFoodMenu = DietNotificationService.updateFoodMenu(dietNotificationDTO);
        return ResponseEntity.ok(updatedFoodMenu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodMenu(@PathVariable Long id) {
        DietNotificationService.deleteFoodMenu(id);
        return ResponseEntity.noContent().build();
    }
}
