package dz.kyrios.notificationservice.controller;

import dz.kyrios.notificationservice.dto.notificationschedule.NotificationScheduleResponse;
import dz.kyrios.notificationservice.service.ManageNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    private final ManageNotificationService manageNotificationService;

    public NotificationController(ManageNotificationService manageNotificationService) {
        this.manageNotificationService = manageNotificationService;
    }

    @GetMapping("/api/v1/notification/not-seen/number")
    public ResponseEntity<Integer> getOne() {
        Integer response = manageNotificationService.getNotSeenNotificationNumber();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
