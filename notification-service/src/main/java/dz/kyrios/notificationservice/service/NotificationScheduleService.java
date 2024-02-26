package dz.kyrios.notificationservice.service;

import dz.kyrios.notificationservice.config.exception.MethodNotAllowedException;
import dz.kyrios.notificationservice.config.exception.NotFoundException;
import dz.kyrios.notificationservice.config.filter.clause.Clause;
import dz.kyrios.notificationservice.config.filter.specification.GenericSpecification;
import dz.kyrios.notificationservice.dto.notificationschedule.NotificationScheduleRequest;
import dz.kyrios.notificationservice.dto.notificationschedule.NotificationScheduleResponse;
import dz.kyrios.notificationservice.entity.NotificationSchedule;
import dz.kyrios.notificationservice.entity.User;
import dz.kyrios.notificationservice.enums.NotificationScheduleStatus;
import dz.kyrios.notificationservice.mapper.notificationschedule.NotificationScheduleMapper;
import dz.kyrios.notificationservice.repository.NotificationScheduleRepository;
import dz.kyrios.notificationservice.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationScheduleService {
    
    private final NotificationScheduleRepository notificationScheduleRepository;

    private final UserRepository userRepository;
    
    private final NotificationScheduleMapper notificationScheduleMapper;

    public NotificationScheduleService(NotificationScheduleRepository notificationScheduleRepository,
                                       UserRepository userRepository,
                                       NotificationScheduleMapper notificationScheduleMapper) {
        this.notificationScheduleRepository = notificationScheduleRepository;
        this.userRepository = userRepository;
        this.notificationScheduleMapper = notificationScheduleMapper;
    }

    public PageImpl<NotificationScheduleResponse> findAllFilter(PageRequest pageRequest, List<Clause> filter) {

        Specification<NotificationSchedule> specification = new GenericSpecification<>(filter);
        List<NotificationScheduleResponse> notificationScheduleResponseList;
        Page<NotificationSchedule> page;
        page = notificationScheduleRepository.findAll(specification, pageRequest);

        notificationScheduleResponseList = page.getContent().stream()
                .map(notificationScheduleMapper::entityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(notificationScheduleResponseList, pageRequest, page.getTotalElements());
    }


    public NotificationScheduleResponse getOne(Long id) {
        NotificationSchedule notificationTemplate = notificationScheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Notification Schedule not found with id: "));
        return notificationScheduleMapper.entityToResponse(notificationTemplate);
    }

    public NotificationSchedule getEntity(Long id) {
        return notificationScheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Notification Schedule not found with id: "));
    }

    public NotificationScheduleResponse create(NotificationScheduleRequest request) {
        if (request.getNotificationTime().isAfter(LocalDateTime.now())) {
            NotificationSchedule entity = notificationScheduleMapper.requestToEntity(request);
            entity.setStatus(NotificationScheduleStatus.EN_ATTENTE);
            return notificationScheduleMapper.entityToResponse(notificationScheduleRepository.save(entity));
        } else {
            throw new RuntimeException("Could not create schedule notification with date before now");
        }
    }

    public NotificationScheduleResponse update(NotificationScheduleRequest request, Long id) {
        NotificationSchedule entity = notificationScheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Notification Schedule not found with id: "));
        if (NotificationScheduleStatus.SENT.equals(entity.getStatus())) {
            throw new MethodNotAllowedException(id, "Could not update Notification Schedule with status Sent with id: ");
        }
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException(request.getUserId(), "User not found with id: "));
        entity.setUser(user);
        entity.setNotificationChannel(request.getNotificationChannel());
        entity.setNotificationTime(request.getNotificationTime());
        entity.setSubject(request.getSubject());
        entity.setBody(request.getBody());

        return notificationScheduleMapper.entityToResponse(entity);
    }

    public void delete(Long id) {
        NotificationSchedule entity = notificationScheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Notification Template not found with id: "));
        if (NotificationScheduleStatus.SENT.equals(entity.getStatus())) {
            throw new MethodNotAllowedException(id, "Could not delete Notification Schedule with status Sent with id: ");
        }
        notificationScheduleRepository.delete(entity);
    }

    // fetch scheduled notification that will send in the next 5 minutes
    public List<NotificationSchedule> fetchPendingNotifications() {
        return notificationScheduleRepository.
                findByNotificationTimeGreaterThanEqualAndNotificationTimeLessThanEqualAndStatus(LocalDateTime.now(), LocalDateTime.now().plusMinutes(5), NotificationScheduleStatus.EN_ATTENTE);
    }

}
