package dz.kyrios.notificationservice.mapper.notificationtemplate;

import dz.kyrios.notificationservice.dto.notificationtemplate.NotificationTemplateRequest;
import dz.kyrios.notificationservice.dto.notificationtemplate.NotificationTemplateResponse;
import dz.kyrios.notificationservice.entity.NotificationTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationTemplateMapperImp implements NotificationTemplateMapper{
    @Override
    public NotificationTemplate requestToEntity(NotificationTemplateRequest request) {
        return NotificationTemplate.builder()
                .id(request.getId())
                .templateCode(request.getTemplateCode())
                .subject(request.getSubject())
                .body(request.getBody())
                .actif(request.getActif())
                .build();
    }

    @Override
    public NotificationTemplateResponse entityToResponse(NotificationTemplate entity) {
        return NotificationTemplateResponse.builder()
                .id(entity.getId())
                .templateCode(entity.getTemplateCode())
                .subject(entity.getSubject())
                .body(entity.getBody())
                .actif(entity.getActif())
                .build();
    }
}
