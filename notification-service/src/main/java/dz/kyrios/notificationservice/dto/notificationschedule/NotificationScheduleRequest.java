package dz.kyrios.notificationservice.dto.notificationschedule;

import dz.kyrios.notificationservice.enums.NotificationChannel;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationScheduleRequest {
    private Long id;
    private Long userId;
    private NotificationChannel notificationChannel;
    private String subject;
    private String body;
    private LocalDateTime notificationTime;
}
