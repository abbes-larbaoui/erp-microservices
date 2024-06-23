package dz.kyrios.adminservice.service;

import dz.kyrios.adminservice.config.exception.NotFoundException;
import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.specification.GenericSpecification;
import dz.kyrios.adminservice.dto.authority.AuthorityRequest;
import dz.kyrios.adminservice.dto.authority.AuthorityResponse;
import dz.kyrios.adminservice.entity.Authority;
import dz.kyrios.adminservice.entity.AuthorityType;
import dz.kyrios.adminservice.entity.Module;
import dz.kyrios.adminservice.mapper.authority.AuthorityMapper;
import dz.kyrios.adminservice.repository.AuthorityRepository;
import dz.kyrios.adminservice.repository.AuthorityTypeRepository;
import dz.kyrios.adminservice.repository.ModuleRepository;
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
public class AuthorityService {
    
    private final AuthorityRepository authorityRepository;

    private final ModuleRepository moduleRepository;

    private final AuthorityTypeRepository authorityTypeRepository;
    
    private final AuthorityMapper authorityMapper;

    public AuthorityService(AuthorityRepository authorityRepository,
                            ModuleRepository moduleRepository,
                            AuthorityTypeRepository authorityTypeRepository,
                            AuthorityMapper authorityMapper) {
        this.authorityRepository = authorityRepository;
        this.moduleRepository = moduleRepository;
        this.authorityTypeRepository = authorityTypeRepository;
        this.authorityMapper = authorityMapper;
    }

    public PageImpl<AuthorityResponse> findAllFilter(PageRequest pageRequest, List<Clause> filter) {

        Specification<Authority> specification = new GenericSpecification<>(filter);
        List<AuthorityResponse> moduleResponseList;
        Page<Authority> page;
        page = authorityRepository.findAll(specification, pageRequest);

        moduleResponseList = page.getContent().stream()
                .map(authorityMapper::entityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(moduleResponseList, pageRequest, page.getTotalElements());
    }

    public AuthorityResponse getOne(Long id) {
        Authority module = authorityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Authority not found with id: "));
        return authorityMapper.entityToResponse(module);
    }

    public AuthorityResponse create(AuthorityRequest request) {
        Authority created = authorityRepository.save(authorityMapper.requestToEntity(request));
        return authorityMapper.entityToResponse(created);
    }

    public AuthorityResponse update(AuthorityRequest request, Long id) {
        Authority entity = authorityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Authority not found with id: "));
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new NotFoundException(request.getModuleId(), "Module not found with id: "));
        AuthorityType authorityType = authorityTypeRepository.findById(request.getAuthorityTypeId())
                .orElseThrow(() -> new NotFoundException(request.getAuthorityTypeId(), "Authority Type not found with id: "));
        entity.setModule(module);
        entity.setAuthorityType(authorityType);
        entity.setLibelle(request.getLibelle());

        return authorityMapper.entityToResponse(entity);
    }

    public void delete(Long id) {
        Authority entity = authorityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Authority not found with id: "));
        authorityRepository.delete(entity);
    }

    public Authority getAuthorityByLibelleAndModule(String libelle, String moduleCode) {
        return authorityRepository.findByLibelleAndModule_ModuleCode(libelle, moduleCode)
                .orElseThrow(() -> new NotFoundException("Authority not found with name: " + libelle + " in module: " + moduleCode));
    }
}
