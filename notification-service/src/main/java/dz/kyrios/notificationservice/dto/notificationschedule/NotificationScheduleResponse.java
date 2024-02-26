package dz.kyrios.notificationservice.dto.notificationschedule;

import dz.kyrios.notificationservice.dto.user.UserResponse;
import dz.kyrios.notificationservice.enums.NotificationChannel;
import dz.kyrios.notificationservice.enums.NotificationScheduleStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationScheduleResponse {
    private Long id;
    private UserResponse user;
    private NotificationChannel notificationChannel;
    private String subject;
    private String body;
    private LocalDateTime notificationTime;
    private NotificationScheduleStatus status;
}
