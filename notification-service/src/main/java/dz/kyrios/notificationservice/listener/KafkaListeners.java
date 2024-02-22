package dz.kyrios.notificationservice.listener;

import dz.kyrios.notificationservice.entity.User;
import dz.kyrios.notificationservice.event.notification.NotificationPayload;
import dz.kyrios.notificationservice.service.ManageNotificationService;
import dz.kyrios.notificationservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListeners {

    private final UserService userService;

    private final ManageNotificationService manageNotificationService;

    public KafkaListeners(UserService userService, ManageNotificationService manageNotificationService) {
        this.userService = userService;
        this.manageNotificationService = manageNotificationService;
    }

    @KafkaListener(id = "userCreateListener", topics = "userCreatedTopic")
    public void create(User user) {
        User createdUser = userService.create(user);
        log.info("User created with userName - {}", createdUser.getUserName());
    }

    @KafkaListener(id = "notificationListener", topics = "notificationTopic")
    public void notify(NotificationPayload payload) {
        manageNotificationService.notification(payload);
//        System.out.println(payload);
//        log.info("Notification sent to user with uuid - {}", payload.getUserUuid());
    }
}
