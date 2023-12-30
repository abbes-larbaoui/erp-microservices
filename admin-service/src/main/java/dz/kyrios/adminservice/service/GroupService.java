package dz.kyrios.adminservice.service;

import dz.kyrios.adminservice.config.exception.NotFoundException;
import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.specification.GenericSpecification;
import dz.kyrios.adminservice.dto.group.GroupResponse;
import dz.kyrios.adminservice.dto.group.GroupRequest;
import dz.kyrios.adminservice.entity.Group;
import dz.kyrios.adminservice.mapper.group.GroupMapper;
import dz.kyrios.adminservice.repository.GroupRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class GroupService {
    
    private final GroupRepository groupRepository;
    
    private final GroupMapper groupMapper;

    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    public PageImpl<GroupResponse> findAllFilter(PageRequest pageRequest, List<Clause> filter) {

        Specification<Group> specification = new GenericSpecification<>(filter);
        List<GroupResponse> groupResponseList;
        Page<Group> page;
        page = groupRepository.findAll(specification, pageRequest);

        groupResponseList = page.getContent().stream()
                .map(groupMapper::entityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(groupResponseList, pageRequest, page.getTotalElements());
    }

    public GroupResponse getOne(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Group not found with id: "));
        return groupMapper.entityToResponse(group);
    }

    public GroupResponse create(GroupRequest request) {
        Group created = groupRepository.save(groupMapper.requestToEntity(request));
        return groupMapper.entityToResponse(created);
    }

    public GroupResponse update(GroupRequest request, Long id) {
        Group entity = groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Group not found with id: "));
        entity.setLibelle(request.getLibelle());

        return groupMapper.entityToResponse(entity);
    }

    public void delete(Long id) {
        Group entity = groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Group not found with id: "));
        groupRepository.delete(entity);
    }
}
