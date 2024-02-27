package dz.kyrios.notificationservice.dto.notification;

import dz.kyrios.notificationservice.dto.user.UserResponse;
import dz.kyrios.notificationservice.entity.User;
import dz.kyrios.notificationservice.enums.NotificationChannel;
import dz.kyrios.notificationservice.enums.NotificationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {
    private Long id;
    private UserResponse user;
    private NotificationChannel notificationChannel;
    private String subject;
    private String body;
    private LocalDateTime notificationTime;
    private NotificationStatus status;
}
