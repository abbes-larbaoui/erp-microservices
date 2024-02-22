package dz.kyrios.notificationservice.service;

import dz.kyrios.notificationservice.config.exception.NotFoundException;
import dz.kyrios.notificationservice.entity.Notification;
import dz.kyrios.notificationservice.entity.User;
import dz.kyrios.notificationservice.enums.NotificationChannel;
import dz.kyrios.notificationservice.enums.NotificationStatus;
import dz.kyrios.notificationservice.event.notification.NotificationPayload;
import dz.kyrios.notificationservice.event.notification.PlaceHolder;
import dz.kyrios.notificationservice.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@Slf4j
public class ManageNotificationService {

    private final NotificationTemplateService notificationTemplateService;

    private final UserService userService;

    private final EmailService emailService;

    private final SmsService smsService;

    private final NotificationRepository notificationRepository;

    public ManageNotificationService(NotificationTemplateService notificationTemplateService,
                                     UserService userService,
                                     EmailService emailService,
                                     SmsService smsService,
                                     NotificationRepository notificationRepository) {
        this.notificationTemplateService = notificationTemplateService;
        this.userService = userService;
        this.emailService = emailService;
        this.smsService = smsService;
        this.notificationRepository = notificationRepository;
    }

    public void notification(NotificationPayload payload) {
        String subject;
        String body;
        if (payload.getSubjectPlaceHolders() != null) {
            subject = replacePlaceholders(notificationTemplateService.getOneByCode(payload.getTemplateCode()).getSubject(), payload.getSubjectPlaceHolders());
        } else {
            subject = notificationTemplateService.getOneByCode(payload.getTemplateCode()).getSubject();
        }
        if (payload.getBodyPlaceHolders() != null) {
            body = replacePlaceholders(notificationTemplateService.getOneByCode(payload.getTemplateCode()).getBody(), payload.getBodyPlaceHolders());
        } else {
            body = notificationTemplateService.getOneByCode(payload.getTemplateCode()).getBody();
        }
        Notification notification = Notification.builder()
                .notificationChannel(payload.getChannel())
                .user(userService.getOne(payload.getUserUuid()))
                .subject(subject)
                .body(body)
                .notificationTime(payload.getTime())
                .status(NotificationStatus.SENT)
                .build();

        switch (notification.getNotificationChannel()) {
            case ALL -> {
                emailService.sendEmail(notification);
                smsService.sendSms(notification);
                pushNotification(notification);
            }
            case EMAIL -> {
                emailService.sendEmail(notification);
            }
            case SMS -> {
                smsService.sendSms(notification);
            }
            case IN_APP -> {
                pushNotification(notification);
            }
        }
    }

    private void pushNotification(Notification notification) {
        log.info("New notification pushed - {}", notification.getSubject());
        log.info("Body - {}", notification.getBody());
    }

    public Integer getNotSeenNotificationNumber(User user) {
        List<NotificationChannel> channels = new ArrayList<>();
        channels.add(NotificationChannel.IN_APP);
        channels.add(NotificationChannel.ALL);
        return notificationRepository.countByStatusAndUserAndNotificationChannelIn(NotificationStatus.SENT, user, channels);
    }

    public void seenNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Notification not found with id: "));
    }

    public static String replacePlaceholders(String inputString, PlaceHolder[] replacements) {
        Pattern pattern = Pattern.compile("\\{\\{(\\d+)\\}\\}"); // Pattern to match placeholders
        Matcher matcher = pattern.matcher(inputString);
        StringBuffer sb = new StringBuffer();

        // Iterate through the placeholders and replace them
        while (matcher.find()) {
            int key = Integer.parseInt(matcher.group(1));
            String value = findValueForKey(replacements, key);
            if (value == null) {
                // Handle missing placeholder value
                throw new IllegalArgumentException("Replacement value not found for placeholder: {{" + key + "}}");
            }
            matcher.appendReplacement(sb, value);
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    private static String findValueForKey(PlaceHolder[] replacements, int key) {
        for (PlaceHolder placeholder : replacements) {
            if (placeholder.getKey() == key) {
                return placeholder.getValue();
            }
        }
        return null;
    }
}
