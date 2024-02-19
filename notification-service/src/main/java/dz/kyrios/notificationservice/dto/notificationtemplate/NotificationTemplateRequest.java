package dz.kyrios.notificationservice.dto.notificationtemplate;

import dz.kyrios.notificationservice.enums.NotificationTemplateCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationTemplateRequest {
    private Long id;
    private NotificationTemplateCode templateCode;
    private String subject;
    private String body;
    private Boolean actif;
}
