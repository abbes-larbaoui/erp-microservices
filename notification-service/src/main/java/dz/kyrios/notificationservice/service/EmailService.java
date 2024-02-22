package dz.kyrios.notificationservice.service;

import dz.kyrios.notificationservice.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class EmailService {
    public void sendEmail(Notification notification) {
        log.info("Notification sent to email address - {}", notification.getUser().getEmail());
        log.info("Subject - {}", notification.getSubject());
        log.info("Body - {}", notification.getBody());
    }
}
