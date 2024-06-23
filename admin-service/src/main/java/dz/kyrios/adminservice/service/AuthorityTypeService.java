package dz.kyrios.adminservice.service;

import dz.kyrios.adminservice.config.exception.NotFoundException;
import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.specification.GenericSpecification;
import dz.kyrios.adminservice.dto.authoritytype.AuthorityTypeRequest;
import dz.kyrios.adminservice.dto.authoritytype.AuthorityTypeResponse;
import dz.kyrios.adminservice.entity.AuthorityType;
import dz.kyrios.adminservice.mapper.authoritytype.AuthorityTypeMapper;
import dz.kyrios.adminservice.repository.AuthorityTypeRepository;
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
public class AuthorityTypeService {
    
    private final AuthorityTypeRepository authorityTypeRepository;
    
    private final AuthorityTypeMapper authorityTypeMapper;

    public AuthorityTypeService(AuthorityTypeRepository authorityTypeRepository, AuthorityTypeMapper authorityTypeMapper) {
        this.authorityTypeRepository = authorityTypeRepository;
        this.authorityTypeMapper = authorityTypeMapper;
    }

    public PageImpl<AuthorityTypeResponse> findAllFilter(PageRequest pageRequest, List<Clause> filter) {

        Specification<AuthorityType> specification = new GenericSpecification<>(filter);
        List<AuthorityTypeResponse> authorityTypeResponseList;
        Page<AuthorityType> page;
        page = authorityTypeRepository.findAll(specification, pageRequest);

        authorityTypeResponseList = page.getContent().stream()
                .map(authorityTypeMapper::entityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(authorityTypeResponseList, pageRequest, page.getTotalElements());
    }

    public AuthorityTypeResponse getOne(Long id) {
        AuthorityType authorityType = authorityTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Authority Type not found with id: "));
        return authorityTypeMapper.entityToResponse(authorityType);
    }

    public AuthorityTypeResponse create(AuthorityTypeRequest request) {
        AuthorityType created = authorityTypeRepository.save(authorityTypeMapper.requestToEntity(request));
        return authorityTypeMapper.entityToResponse(created);
    }

    public AuthorityTypeResponse update(AuthorityTypeRequest request, Long id) {
        AuthorityType entity = authorityTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Authority Type not found with id: "));
        entity.setLibelle(request.getLibelle());

        return authorityTypeMapper.entityToResponse(entity);
    }

    public void delete(Long id) {
        AuthorityType entity = authorityTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Authority Type not found with id: "));
        authorityTypeRepository.delete(entity);
    }
}
