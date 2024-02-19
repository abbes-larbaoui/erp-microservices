package dz.kyrios.adminservice.event.notification;

import dz.kyrios.adminservice.enums.NotificationChannel;
import dz.kyrios.adminservice.enums.NotificationTemplateCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class NotificationPayload {
    private String userUuid;
    private NotificationTemplateCode templateCode;
    private PlaceHolder[] subjectPlaceHolders;
    private PlaceHolder[] bodyPlaceHolders;
    private NotificationChannel channel;
    private LocalDateTime time;
}
