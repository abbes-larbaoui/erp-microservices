package dz.kyrios.notificationservice.service;

import dz.kyrios.notificationservice.config.exception.NotFoundException;
import dz.kyrios.notificationservice.config.filter.clause.Clause;
import dz.kyrios.notificationservice.config.filter.clause.ClauseArrayArgs;
import dz.kyrios.notificationservice.config.filter.clause.ClauseOneArg;
import dz.kyrios.notificationservice.config.filter.enums.Operation;
import dz.kyrios.notificationservice.config.filter.specification.GenericSpecification;
import dz.kyrios.notificationservice.dto.notification.NotificationResponse;
import dz.kyrios.notificationservice.entity.Notification;
import dz.kyrios.notificationservice.entity.NotificationSchedule;
import dz.kyrios.notificationservice.entity.User;
import dz.kyrios.notificationservice.enums.NotificationChannel;
import dz.kyrios.notificationservice.enums.NotificationScheduleStatus;
import dz.kyrios.notificationservice.enums.NotificationStatus;
import dz.kyrios.notificationservice.event.notification.NotificationPayload;
import dz.kyrios.notificationservice.event.notification.PlaceHolder;
import dz.kyrios.notificationservice.mapper.notification.NotificationMapper;
import dz.kyrios.notificationservice.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ManageNotificationService {

    private final NotificationTemplateService notificationTemplateService;

    private final NotificationScheduleService notificationScheduleService;

    private final NotificationMapper notificationMapper;

    private final UserService userService;

    private final EmailService emailService;

    private final SmsService smsService;

    private final NotificationRepository notificationRepository;

    public ManageNotificationService(NotificationTemplateService notificationTemplateService,
                                     NotificationScheduleService notificationScheduleService,
                                     NotificationMapper notificationMapper,
                                     UserService userService,
                                     EmailService emailService,
                                     SmsService smsService,
                                     NotificationRepository notificationRepository) {
        this.notificationTemplateService = notificationTemplateService;
        this.notificationScheduleService = notificationScheduleService;
        this.notificationMapper = notificationMapper;
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
        Notification entity = notificationRepository.save(notification);
        pushNotification(entity);
    }

    private void pushNotification(Notification notification) {
        switch (notification.getNotificationChannel()) {
            case ALL -> {
                emailService.sendEmail(notification);
                smsService.sendSms(notification);
                inAppNotification(notification);
            }
            case EMAIL -> {
                emailService.sendEmail(notification);
            }
            case SMS -> {
                smsService.sendSms(notification);
            }
            case IN_APP -> {
                inAppNotification(notification);
            }
        }
    }

    // TODO: implimentation of in app notification
    private void inAppNotification(Notification notification) {
        log.info("New notification pushed - {}", notification.getSubject());
        log.info("Body - {}", notification.getBody());
    }

    public Integer getNotSeenNotificationNumber() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
        Jwt jwt = jwtAuthentication.getToken();

        String username = (String) jwt.getClaims().get("preferred_username");
        User user = userService.getByUsername(username);

        List<NotificationChannel> channels = new ArrayList<>();
        channels.add(NotificationChannel.IN_APP);
        channels.add(NotificationChannel.ALL);
        return notificationRepository.countByStatusAndUserAndNotificationChannelIn(NotificationStatus.SENT, user, channels);
    }

    public PageImpl<NotificationResponse> findAllFilterForUser(PageRequest pageRequest, List<Clause> filter) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
        Jwt jwt = jwtAuthentication.getToken();

        String username = (String) jwt.getClaims().get("preferred_username");
        User user = userService.getByUsername(username);
        Clause userClause = new ClauseOneArg("user.id", Operation.Equals, user.getId().toString());

        String[] channels = {String.valueOf(NotificationChannel.ALL), String.valueOf(NotificationChannel.IN_APP)};
//        NotificationChannel[] channels = {NotificationChannel.ALL, NotificationChannel.IN_APP};
        Clause channelsClause = new ClauseArrayArgs("notificationChannel", Operation.In, channels);

        filter.add(userClause);
        filter.add(channelsClause);
        Specification<Notification> specification = new GenericSpecification<>(filter);
        List<NotificationResponse> notificationResponseList;
        Page<Notification> page;
        page = notificationRepository.findAll(specification, pageRequest);

        notificationResponseList = page.getContent().stream()
                .map(notificationMapper::entityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(notificationResponseList, pageRequest, page.getTotalElements());
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

    // send scheduled notifications
    @Scheduled(fixedRate = 300000) /* Check every 5 minutes for new notifications */
    public void checkForScheduledNotifications() {
        log.warn("Schedule excuted ===================");
        List<NotificationSchedule> pendingNotifications = notificationScheduleService.fetchPendingNotifications();

        for (NotificationSchedule notificationSchedule : pendingNotifications) {
            /* save the notification in db */
            Notification entity = notificationRepository.save(notificationScheduleToNotification(notificationSchedule));

            /* edit status of scheduled notification to SENT */
            notificationScheduleService.getEntity(notificationSchedule.getId()).setStatus(NotificationScheduleStatus.SENT);

            /* push the notification */
            pushNotification(entity);

            log.warn("notification sent ===================");
        }
    }

    public Notification notificationScheduleToNotification(NotificationSchedule schedule) {
        return Notification.builder()
                .notificationChannel(schedule.getNotificationChannel())
                .user(schedule.getUser())
                .notificationTime(schedule.getNotificationTime())
                .subject(schedule.getSubject())
                .body(schedule.getBody())
                .status(NotificationStatus.SENT)
                .build();
    }
}
