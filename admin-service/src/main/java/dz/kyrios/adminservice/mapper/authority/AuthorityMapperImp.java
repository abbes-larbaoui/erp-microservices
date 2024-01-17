package dz.kyrios.adminservice.mapper.authority;

import dz.kyrios.adminservice.config.exception.NotFoundException;
import dz.kyrios.adminservice.dto.authority.AuthorityRequest;
import dz.kyrios.adminservice.dto.authority.AuthorityResponse;
import dz.kyrios.adminservice.entity.Authority;
import dz.kyrios.adminservice.entity.AuthorityType;
import dz.kyrios.adminservice.entity.Module;
import dz.kyrios.adminservice.mapper.authoritytype.AuthorityTypeMapper;
import dz.kyrios.adminservice.mapper.module.ModuleMapper;
import dz.kyrios.adminservice.repository.AuthorityTypeRepository;
import dz.kyrios.adminservice.repository.ModuleRepository;
import org.springframework.stereotype.Component;

@Component
public class AuthorityMapperImp implements AuthorityMapper{

    private final AuthorityTypeRepository authorityTypeRepository;

    private final ModuleRepository moduleRepository;

    private final AuthorityTypeMapper authorityTypeMapper;

    private final ModuleMapper moduleMapper;

    public AuthorityMapperImp(AuthorityTypeRepository authorityTypeRepository,
                              ModuleRepository moduleRepository,
                              AuthorityTypeMapper authorityTypeMapper,
                              ModuleMapper moduleMapper) {
        this.authorityTypeRepository = authorityTypeRepository;
        this.moduleRepository = moduleRepository;
        this.authorityTypeMapper = authorityTypeMapper;
        this.moduleMapper = moduleMapper;
    }

    @Override
    public Authority requestToEntity(AuthorityRequest request) {
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new NotFoundException(request.getModuleId(), "Module not found with id: "));
        AuthorityType authorityType = authorityTypeRepository.findById(request.getAuthorityTypeId())
                .orElseThrow(() -> new NotFoundException(request.getAuthorityTypeId(), "Authority Type not found with id: "));

        return Authority.builder()
                .id(request.getId())
                .libelle(request.getLibelle())
                .module(module)
                .authorityType(authorityType)
                .actif(request.getActif())
                .build();
    }

    @Override
    public AuthorityResponse entityToResponse(Authority entity) {
        return AuthorityResponse.builder()
                .authorityTypeResponse(authorityTypeMapper.entityToResponse(entity.getAuthorityType()))
                .moduleResponse(moduleMapper.entityToResponse(entity.getModule()))
                .libelle(entity.getLibelle())
                .id(entity.getId())
                .actif(entity.getActif())
                .build();
    }
}
