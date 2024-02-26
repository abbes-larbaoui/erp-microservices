package dz.kyrios.notificationservice.mapper.notificationschedule;

import dz.kyrios.notificationservice.config.exception.NotFoundException;
import dz.kyrios.notificationservice.dto.notificationschedule.NotificationScheduleRequest;
import dz.kyrios.notificationservice.dto.notificationschedule.NotificationScheduleResponse;
import dz.kyrios.notificationservice.entity.NotificationSchedule;
import dz.kyrios.notificationservice.mapper.user.UserMapper;
import dz.kyrios.notificationservice.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduleMapperImp implements NotificationScheduleMapper{

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public NotificationScheduleMapperImp(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public NotificationSchedule requestToEntity(NotificationScheduleRequest request) {
        return NotificationSchedule.builder()
                .user(userRepository.findById(request.getUserId())
                        .orElseThrow(() -> new NotFoundException(request.getUserId(), "User not found with id: ")))
                .id(request.getId())
                .subject(request.getSubject())
                .body(request.getBody())
                .notificationTime(request.getNotificationTime())
                .notificationChannel(request.getNotificationChannel())
                .build();
    }

    @Override
    public NotificationScheduleResponse entityToResponse(NotificationSchedule entity) {
        return NotificationScheduleResponse.builder()
                .id(entity.getId())
                .user(userMapper.entityToResponse(entity.getUser()))
                .status(entity.getStatus())
                .notificationChannel(entity.getNotificationChannel())
                .notificationTime(entity.getNotificationTime())
                .subject(entity.getSubject())
                .body(entity.getBody())
                .build();
    }
}
