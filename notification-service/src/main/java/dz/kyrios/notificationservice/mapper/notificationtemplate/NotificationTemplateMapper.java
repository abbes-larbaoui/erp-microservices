package dz.kyrios.notificationservice.mapper.notificationtemplate;

import dz.kyrios.notificationservice.dto.notificationtemplate.NotificationTemplateRequest;
import dz.kyrios.notificationservice.dto.notificationtemplate.NotificationTemplateResponse;
import dz.kyrios.notificationservice.entity.NotificationTemplate;

public interface NotificationTemplateMapper {

    NotificationTemplate requestToEntity(NotificationTemplateRequest request);

    NotificationTemplateResponse entityToResponse(NotificationTemplate entity);
}
