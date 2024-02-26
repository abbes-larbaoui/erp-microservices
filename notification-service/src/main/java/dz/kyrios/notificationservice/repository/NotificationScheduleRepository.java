package dz.kyrios.notificationservice.repository;

import dz.kyrios.notificationservice.entity.NotificationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationScheduleRepository extends JpaRepository<NotificationSchedule, Long>, JpaSpecificationExecutor<NotificationSchedule> {
}
