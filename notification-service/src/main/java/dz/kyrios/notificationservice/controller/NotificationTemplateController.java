package dz.kyrios.notificationservice.controller;

import dz.kyrios.notificationservice.config.filter.clause.Clause;
import dz.kyrios.notificationservice.config.filter.clause.ClauseOneArg;
import dz.kyrios.notificationservice.config.filter.handlerMethodArgumentResolver.Critiria;
import dz.kyrios.notificationservice.config.filter.handlerMethodArgumentResolver.SearchValue;
import dz.kyrios.notificationservice.config.filter.handlerMethodArgumentResolver.SortParam;
import dz.kyrios.notificationservice.dto.notificationtemplate.NotificationTemplateRequest;
import dz.kyrios.notificationservice.dto.notificationtemplate.NotificationTemplateResponse;
import dz.kyrios.notificationservice.service.NotificationTemplateService;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationTemplateController {
    
    private final NotificationTemplateService notificationTemplateService;

    public NotificationTemplateController(NotificationTemplateService notificationTemplateService) {
        this.notificationTemplateService = notificationTemplateService;
    }

    @GetMapping("/api/v1/notification-template")
    public PageImpl<NotificationTemplateResponse> getAllFilter(@SortParam PageRequest pageRequest,
                                                               @Critiria List<Clause> filter,
                                                               @SearchValue ClauseOneArg searchValue) {
        filter.add(searchValue);
        return notificationTemplateService.findAllFilter(pageRequest, filter);
    }

    @GetMapping("/api/v1/notification-template/{id}")
    public ResponseEntity<NotificationTemplateResponse> getOne(@PathVariable Long id) {
        NotificationTemplateResponse response = notificationTemplateService.getOne(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/v1/notification-template")
    public ResponseEntity<NotificationTemplateResponse> create(@RequestBody NotificationTemplateRequest request) {
        NotificationTemplateResponse response = notificationTemplateService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/api/v1/notification-template/{id}")
    public ResponseEntity<NotificationTemplateResponse> update(@RequestBody NotificationTemplateRequest request,@PathVariable Long id) {
        NotificationTemplateResponse response = notificationTemplateService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/notification-template/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            notificationTemplateService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
