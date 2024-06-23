package dz.kyrios.notificationservice.service;

import dz.kyrios.notificationservice.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.internet.MimeMessage;

@Service
@Transactional
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(Notification notification) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("abbemeiouna@gmail.com", "Kyrios ERP");
            helper.setTo(notification.getUser().getEmail());

            helper.setSubject(notification.getSubject());
            helper.setText(notification.getBody(), true);

            mailSender.send(message);
        } catch (Exception e) {
            log.error("Exception - {}", e.getMessage());
        }

    }

//    public void sendEmail(Notification notification) {
//        log.info("Notification sent to email address - {}", notification.getUser().getEmail());
//        log.info("Subject - {}", notification.getSubject());
//        log.info("Body - {}", notification.getBody());
//    }
}
