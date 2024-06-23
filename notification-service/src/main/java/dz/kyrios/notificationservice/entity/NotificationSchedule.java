package dz.kyrios.notificationservice.entity;

import dz.kyrios.notificationservice.enums.NotificationChannel;
import dz.kyrios.notificationservice.enums.NotificationScheduleStatus;
import dz.kyrios.notificationservice.enums.NotificationStatus;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "notification_schedule")
public class NotificationSchedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_chanel", nullable = false)
    private NotificationChannel notificationChannel;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body")
    private String body;

    @Column(name = "notification_time")
    private LocalDateTime notificationTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_schedule_status", nullable = false)
    private NotificationScheduleStatus status;
}
