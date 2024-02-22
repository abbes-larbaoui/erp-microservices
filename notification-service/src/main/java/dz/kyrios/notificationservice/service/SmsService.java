package dz.kyrios.notificationservice.service;

import dz.kyrios.notificationservice.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class SmsService {
    public void sendSms(Notification notification) {
        log.info("Notification sent to email address - {}", notification.getUser().getPhoneNumber());
        log.info("Body - {}", notification.getBody());
    }
}
