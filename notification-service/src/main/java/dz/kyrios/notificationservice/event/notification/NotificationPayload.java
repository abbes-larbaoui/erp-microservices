package dz.kyrios.notificationservice.event.notification;

import dz.kyrios.notificationservice.enums.NotificationChannel;
import dz.kyrios.notificationservice.enums.NotificationTemplateCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPayload {
    private String userUuid;
    private NotificationTemplateCode templateCode;
    private PlaceHolder[] subjectPlaceHolders;
    private PlaceHolder[] bodyPlaceHolders;
    private NotificationChannel channel;
    private LocalDateTime time;
}
