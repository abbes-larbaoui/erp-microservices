package dz.kyrios.adminservice.service;

import dz.kyrios.adminservice.config.exception.NotFoundException;
import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.specification.GenericSpecification;
import dz.kyrios.adminservice.dto.module.ModuleRequest;
import dz.kyrios.adminservice.dto.module.ModuleResponse;
import dz.kyrios.adminservice.entity.Module;
import dz.kyrios.adminservice.mapper.module.ModuleMapper;
import dz.kyrios.adminservice.repository.ModuleRepository;
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
public class ModuleService {

    private final ModuleRepository moduleRepository;

    private final ModuleMapper moduleMapper;

    public ModuleService(ModuleRepository moduleRepository, ModuleMapper moduleMapper) {
        this.moduleRepository = moduleRepository;
        this.moduleMapper = moduleMapper;
    }

//    @PreAuthorize("hasAuthority('agence-list')")
    public PageImpl<ModuleResponse> findAllFilter(PageRequest pageRequest, List<Clause> filter) {

        Specification<Module> specification = new GenericSpecification<>(filter);
        List<ModuleResponse> moduleResponseList;
        Page<Module> page;
        page = moduleRepository.findAll(specification, pageRequest);

        moduleResponseList = page.getContent().stream()
                .map(moduleMapper::entityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(moduleResponseList, pageRequest, page.getTotalElements());
    }

    public ModuleResponse getOne(Long id) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Module not found with id: "));
        return moduleMapper.entityToResponse(module);
    }

    public Module getOneBycode(String code) {
        return moduleRepository.findByModuleCode(code)
                .orElseThrow(() -> new NotFoundException("Module not found with code: " + code));
    }

    public ModuleResponse create(ModuleRequest request) {
        Module created = moduleRepository.save(moduleMapper.requestToEntity(request));
        return moduleMapper.entityToResponse(created);
    }

    public ModuleResponse update(ModuleRequest request, Long id) {
        Module entity = moduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Module not found with id: "));
        entity.setModuleName(request.getModuleName());
        entity.setColor(request.getColor());
        entity.setIcon(request.getIcon());
        entity.setUri(request.getUri());

        return moduleMapper.entityToResponse(entity);
    }

    public void delete(Long id) {
        Module entity = moduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Module not found with id: "));
        moduleRepository.delete(entity);
    }
}
