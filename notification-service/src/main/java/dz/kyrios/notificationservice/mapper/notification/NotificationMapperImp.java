package dz.kyrios.notificationservice.mapper.notification;

import dz.kyrios.notificationservice.dto.notification.NotificationResponse;
import dz.kyrios.notificationservice.entity.Notification;
import dz.kyrios.notificationservice.mapper.user.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapperImp implements NotificationMapper{

    private final UserMapper userMapper;

    public NotificationMapperImp(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public NotificationResponse entityToResponse(Notification entity) {
        return NotificationResponse.builder()
                .id(entity.getId())
                .user(userMapper.entityToResponse(entity.getUser()))
                .notificationChannel(entity.getNotificationChannel())
                .status(entity.getStatus())
                .notificationTime(entity.getNotificationTime())
                .subject(entity.getSubject())
                .body(entity.getBody())
                .build();
    }
}
