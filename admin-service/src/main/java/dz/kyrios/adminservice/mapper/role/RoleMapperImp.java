package dz.kyrios.adminservice.mapper.role;

import dz.kyrios.adminservice.config.exception.NotFoundException;
import dz.kyrios.adminservice.dto.authority.AuthorityResponse;
import dz.kyrios.adminservice.dto.role.RoleRequest;
import dz.kyrios.adminservice.dto.role.RoleResponse;
import dz.kyrios.adminservice.entity.Module;
import dz.kyrios.adminservice.entity.Role;
import dz.kyrios.adminservice.mapper.authority.AuthorityMapper;
import dz.kyrios.adminservice.mapper.module.ModuleMapper;
import dz.kyrios.adminservice.repository.ModuleRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RoleMapperImp implements RoleMapper{

    private final ModuleRepository moduleRepository;

    private final ModuleMapper moduleMapper;

    private final AuthorityMapper authorityMapper;

    public RoleMapperImp(ModuleRepository moduleRepository,
                         ModuleMapper moduleMapper,
                         AuthorityMapper authorityMapper) {
        this.moduleRepository = moduleRepository;
        this.moduleMapper = moduleMapper;
        this.authorityMapper = authorityMapper;
    }

    @Override
    public Role requestToEntity(RoleRequest request) {
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new NotFoundException(request.getModuleId(), "Module not found with id: "));
        return Role.builder()
                .id(request.getId())
                .module(module)
                .libelle(request.getLibelle())
                .actif(request.getActif())
                .build();
    }

    @Override
    public RoleResponse entityToResponse(Role entity) {
        Set<AuthorityResponse> authorityResponses = new HashSet<>();
        if (entity.getAuthorities() != null) {
            entity.getAuthorities().forEach(authority -> {
                AuthorityResponse authorityResponse = authorityMapper.entityToResponse(authority);
                authorityResponses.add(authorityResponse);
            });
        }
        return RoleResponse.builder()
                .id(entity.getId())
                .libelle(entity.getLibelle())
                .moduleResponse(moduleMapper.entityToResponse(entity.getModule()))
                .authorityResponses(authorityResponses)
                .actif(entity.getActif())
                .build();
    }
}
