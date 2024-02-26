package dz.kyrios.notificationservice.repository;

import dz.kyrios.notificationservice.entity.NotificationSchedule;
import dz.kyrios.notificationservice.enums.NotificationScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationScheduleRepository extends JpaRepository<NotificationSchedule, Long>, JpaSpecificationExecutor<NotificationSchedule> {
    List<NotificationSchedule> findByNotificationTimeGreaterThanEqualAndNotificationTimeLessThanEqualAndStatus(LocalDateTime start, LocalDateTime end, NotificationScheduleStatus status);
}
