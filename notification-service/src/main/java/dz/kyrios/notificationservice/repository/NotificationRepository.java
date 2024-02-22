package dz.kyrios.notificationservice.repository;

import dz.kyrios.notificationservice.entity.Notification;

import dz.kyrios.notificationservice.entity.User;
import dz.kyrios.notificationservice.enums.NotificationChannel;
import dz.kyrios.notificationservice.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {
    Integer countByStatusAndUserAndNotificationChannelIn(NotificationStatus status, User user, Collection<NotificationChannel> channels);
}
