package dz.kyrios.notificationservice.controller;

import dz.kyrios.notificationservice.config.filter.clause.Clause;
import dz.kyrios.notificationservice.config.filter.clause.ClauseOneArg;
import dz.kyrios.notificationservice.config.filter.handlerMethodArgumentResolver.Critiria;
import dz.kyrios.notificationservice.config.filter.handlerMethodArgumentResolver.SearchValue;
import dz.kyrios.notificationservice.config.filter.handlerMethodArgumentResolver.SortParam;
import dz.kyrios.notificationservice.dto.notification.NotificationResponse;
import dz.kyrios.notificationservice.dto.notificationschedule.NotificationScheduleResponse;
import dz.kyrios.notificationservice.service.ManageNotificationService;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {

    private final ManageNotificationService manageNotificationService;

    public NotificationController(ManageNotificationService manageNotificationService) {
        this.manageNotificationService = manageNotificationService;
    }

    @GetMapping("/api/v1/notification/user/not-seen/number")
    public ResponseEntity<Integer> getOne() {
        Integer response = manageNotificationService.getNotSeenNotificationNumber();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/v1/notification/user")
    public PageImpl<NotificationResponse> getAllFilter(@SortParam PageRequest pageRequest,
                                                       @Critiria List<Clause> filter,
                                                       @SearchValue ClauseOneArg searchValue) {
        filter.add(searchValue);
        return manageNotificationService.findAllFilterForUser(pageRequest, filter);
    }
}
