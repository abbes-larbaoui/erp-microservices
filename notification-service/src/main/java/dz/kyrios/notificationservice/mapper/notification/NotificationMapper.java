package dz.kyrios.notificationservice.mapper.notification;

import dz.kyrios.notificationservice.dto.notification.NotificationResponse;
import dz.kyrios.notificationservice.entity.Notification;

public interface NotificationMapper {
    NotificationResponse entityToResponse(Notification entity);
}
