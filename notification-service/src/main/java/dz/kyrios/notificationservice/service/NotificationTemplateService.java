package dz.kyrios.notificationservice.service;

import dz.kyrios.notificationservice.config.exception.NotFoundException;
import dz.kyrios.notificationservice.dto.notificationtemplate.NotificationTemplateRequest;
import dz.kyrios.notificationservice.dto.notificationtemplate.NotificationTemplateResponse;
import dz.kyrios.notificationservice.entity.NotificationTemplate;
import dz.kyrios.notificationservice.mapper.notificationtemplate.NotificationTemplateMapper;
import dz.kyrios.notificationservice.repository.NotificationTemplateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationTemplateService {

    private final NotificationTemplateRepository notificationTemplateRepository;

    private final NotificationTemplateMapper notificationTemplateMapper;

    public NotificationTemplateService(NotificationTemplateRepository notificationTemplateRepository, NotificationTemplateMapper notificationTemplateMapper) {
        this.notificationTemplateRepository = notificationTemplateRepository;
        this.notificationTemplateMapper = notificationTemplateMapper;
    }

//    public PageImpl<AuthorityTypeResponse> findAllFilter(PageRequest pageRequest, List<Clause> filter) {
//
//        Specification<AuthorityType> specification = new GenericSpecification<>(filter);
//        List<AuthorityTypeResponse> authorityTypeResponseList;
//        Page<AuthorityType> page;
//        page = notificationTemplateRepository.findAll(specification, pageRequest);
//
//        authorityTypeResponseList = page.getContent().stream()
//                .map(notificationTemplateMapper::entityToResponse)
//                .collect(Collectors.toList());
//        return new PageImpl<>(authorityTypeResponseList, pageRequest, page.getTotalElements());
//    }

    public NotificationTemplateResponse getOne(Long id) {
        NotificationTemplate notificationTemplate = notificationTemplateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Notification Template not found with id: "));
        return notificationTemplateMapper.entityToResponse(notificationTemplate);
    }

    public NotificationTemplateResponse create(NotificationTemplateRequest request) {
        NotificationTemplate created = notificationTemplateRepository.save(notificationTemplateMapper.requestToEntity(request));
        return notificationTemplateMapper.entityToResponse(created);
    }

    public NotificationTemplateResponse update(NotificationTemplateRequest request, Long id) {
        NotificationTemplate entity = notificationTemplateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Notification Template not found with id: "));
        entity.setTemplateCode(request.getTemplateCode());
        entity.setSubject(request.getSubject());
        entity.setBody(request.getBody());

        return notificationTemplateMapper.entityToResponse(entity);
    }

    public void delete(Long id) {
        NotificationTemplate entity = notificationTemplateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Notification Template not found with id: "));
        notificationTemplateRepository.delete(entity);
    }
}
