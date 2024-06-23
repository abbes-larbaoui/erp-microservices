package dz.kyrios.notificationservice.controller;

import dz.kyrios.notificationservice.config.exception.MethodNotAllowedException;
import dz.kyrios.notificationservice.config.exception.NotFoundException;
import dz.kyrios.notificationservice.config.filter.clause.Clause;
import dz.kyrios.notificationservice.config.filter.clause.ClauseOneArg;
import dz.kyrios.notificationservice.config.filter.handlerMethodArgumentResolver.Critiria;
import dz.kyrios.notificationservice.config.filter.handlerMethodArgumentResolver.SearchValue;
import dz.kyrios.notificationservice.config.filter.handlerMethodArgumentResolver.SortParam;
import dz.kyrios.notificationservice.dto.notificationschedule.NotificationScheduleRequest;
import dz.kyrios.notificationservice.dto.notificationschedule.NotificationScheduleResponse;
import dz.kyrios.notificationservice.service.NotificationScheduleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationScheduleController {

    private final NotificationScheduleService notificationScheduleService;

    public NotificationScheduleController(NotificationScheduleService notificationScheduleService) {
        this.notificationScheduleService = notificationScheduleService;
    }

    @GetMapping("/api/v1/notification-schedule")
    @PreAuthorize("@authz.hasCustomAuthority('NOTIFICATION_SCHEDULE_LIST')")
//    @PreAuthorize("hasCustomAuthority('NOTIFICATION_SCHEDULE_LIST')")
    public ResponseEntity<Object> getAllFilter(@SortParam PageRequest pageRequest,
                                               @Critiria List<Clause> filter,
                                               @SearchValue ClauseOneArg searchValue) {
        try {
            filter.add(searchValue);
            return new ResponseEntity<>(notificationScheduleService.findAllFilter(pageRequest, filter), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/v1/notification-schedule/{id}")
    @PreAuthorize("@authz.hasCustomAuthority('NOTIFICATION_SCHEDULE_VIEW')")
//    @PreAuthorize("hasCustomAuthority('NOTIFICATION_SCHEDULE_VIEW')")
    public ResponseEntity<Object> getOne(@PathVariable Long id) {
        try {
            NotificationScheduleResponse response = notificationScheduleService.getOne(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/v1/notification-schedule")
    @PreAuthorize("@authz.hasCustomAuthority('NOTIFICATION_SCHEDULE_CREATE')")
//    @PreAuthorize("hasCustomAuthority('NOTIFICATION_SCHEDULE_CREATE')")
    public ResponseEntity<Object> create(@RequestBody NotificationScheduleRequest request) {
        try {
            NotificationScheduleResponse response = notificationScheduleService.create(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/v1/notification-schedule/{id}")
    @PreAuthorize("@authz.hasCustomAuthority('NOTIFICATION_SCHEDULE_UPDATE')")
//    @PreAuthorize("hasCustomAuthority('NOTIFICATION_SCHEDULE_UPDATE')")
    public ResponseEntity<Object> update(@RequestBody NotificationScheduleRequest request,
                                         @PathVariable Long id) {
        try {
            NotificationScheduleResponse response = notificationScheduleService.update(request, id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/v1/notification-schedule/{id}")
    @PreAuthorize("@authz.hasCustomAuthority('NOTIFICATION_SCHEDULE_DELETE')")
//    @PreAuthorize("hasCustomAuthority('NOTIFICATION_SCHEDULE_DELETE')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            notificationScheduleService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (MethodNotAllowedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
