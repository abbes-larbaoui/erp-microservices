package dz.kyrios.notificationservice.controller;

import dz.kyrios.notificationservice.config.exception.NotFoundException;
import dz.kyrios.notificationservice.config.filter.clause.Clause;
import dz.kyrios.notificationservice.config.filter.clause.ClauseOneArg;
import dz.kyrios.notificationservice.config.filter.handlerMethodArgumentResolver.Critiria;
import dz.kyrios.notificationservice.config.filter.handlerMethodArgumentResolver.SearchValue;
import dz.kyrios.notificationservice.config.filter.handlerMethodArgumentResolver.SortParam;
import dz.kyrios.notificationservice.dto.notificationtemplate.NotificationTemplateRequest;
import dz.kyrios.notificationservice.dto.notificationtemplate.NotificationTemplateResponse;
import dz.kyrios.notificationservice.service.NotificationTemplateService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationTemplateController {

    private final NotificationTemplateService notificationTemplateService;

    public NotificationTemplateController(NotificationTemplateService notificationTemplateService) {
        this.notificationTemplateService = notificationTemplateService;
    }

    @GetMapping("/api/v1/notification-template")
    @PreAuthorize("@authz.hasCustomAuthority('NOTIFICATION_TEMPLATE_LIST')")
//    @PreAuthorize("hasCustomAuthority('NOTIFICATION_TEMPLATE_LIST')")
    public ResponseEntity<Object> getAllFilter(@SortParam PageRequest pageRequest,
                                               @Critiria List<Clause> filter,
                                               @SearchValue ClauseOneArg searchValue) {
        try {
            filter.add(searchValue);
            return new ResponseEntity<>(notificationTemplateService.findAllFilter(pageRequest, filter), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/v1/notification-template/{id}")
    @PreAuthorize("@authz.hasCustomAuthority('NOTIFICATION_TEMPLATE_VIEW')")
//    @PreAuthorize("hasCustomAuthority('NOTIFICATION_TEMPLATE_VIEW')")
    public ResponseEntity<Object> getOne(@PathVariable Long id) {
        try {
            NotificationTemplateResponse response = notificationTemplateService.getOne(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/v1/notification-template")
    @PreAuthorize("@authz.hasCustomAuthority('NOTIFICATION_TEMPLATE_CREATE')")
//    @PreAuthorize("hasCustomAuthority('NOTIFICATION_TEMPLATE_CREATE')")
    public ResponseEntity<Object> create(@RequestBody NotificationTemplateRequest request) {
        try {
            NotificationTemplateResponse response = notificationTemplateService.create(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/v1/notification-template/{id}")
    @PreAuthorize("@authz.hasCustomAuthority('NOTIFICATION_TEMPLATE_UPDATE')")
//    @PreAuthorize("hasCustomAuthority('NOTIFICATION_TEMPLATE_UPDATE')")
    public ResponseEntity<Object> update(@RequestBody NotificationTemplateRequest request,
                                         @PathVariable Long id) {
        try {
            NotificationTemplateResponse response = notificationTemplateService.update(request, id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/v1/notification-template/{id}")
    @PreAuthorize("@authz.hasCustomAuthority('NOTIFICATION_TEMPLATE_DELETE')")
//    @PreAuthorize("hasCustomAuthority('NOTIFICATION_TEMPLATE_DELETE')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            notificationTemplateService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
