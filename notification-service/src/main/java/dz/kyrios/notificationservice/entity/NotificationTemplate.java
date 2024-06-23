package dz.kyrios.notificationservice.entity;

import dz.kyrios.notificationservice.enums.NotificationTemplateCode;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "notification_template")
public class NotificationTemplate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "template_code", nullable = false, unique = true)
    private NotificationTemplateCode templateCode;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body", columnDefinition = "TEXT not null")
    private String body;

    @Column(name = "actif")
    private Boolean actif = Boolean.TRUE;
}
