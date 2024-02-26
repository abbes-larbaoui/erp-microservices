package dz.kyrios.notificationservice.mapper.notificationschedule;

import dz.kyrios.notificationservice.dto.notificationschedule.NotificationScheduleRequest;
import dz.kyrios.notificationservice.dto.notificationschedule.NotificationScheduleResponse;
import dz.kyrios.notificationservice.entity.NotificationSchedule;

public interface NotificationScheduleMapper {

    NotificationSchedule requestToEntity(NotificationScheduleRequest request);

    NotificationScheduleResponse entityToResponse(NotificationSchedule entity);
}
