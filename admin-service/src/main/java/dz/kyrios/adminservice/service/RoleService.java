package dz.kyrios.adminservice.service;

import dz.kyrios.adminservice.config.exception.NotFoundException;
import dz.kyrios.adminservice.config.exception.NotMatchException;
import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.specification.GenericSpecification;
import dz.kyrios.adminservice.dto.role.RoleRequest;
import dz.kyrios.adminservice.dto.role.RoleResponse;
import dz.kyrios.adminservice.entity.Authority;
import dz.kyrios.adminservice.entity.Module;
import dz.kyrios.adminservice.entity.Role;
import dz.kyrios.adminservice.mapper.role.RoleMapper;
import dz.kyrios.adminservice.repository.AuthorityRepository;
import dz.kyrios.adminservice.repository.ModuleRepository;
import dz.kyrios.adminservice.repository.RoleRepository;
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
public class RoleService {
    
    private final RoleRepository roleRepository;

    private final ModuleRepository moduleRepository;

    private final AuthorityRepository authorityRepository;
    
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository,
                       ModuleRepository moduleRepository,
                       AuthorityRepository authorityRepository,
                       RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.moduleRepository = moduleRepository;
        this.authorityRepository = authorityRepository;
        this.roleMapper = roleMapper;
    }

    public PageImpl<RoleResponse> findAllFilterByModule(PageRequest pageRequest, List<Clause> filter) {

        Specification<Role> specification = new GenericSpecification<>(filter);
        List<RoleResponse> roleResponseList;
        Page<Role> page;
        page = roleRepository.findAll(specification, pageRequest);

        roleResponseList = page.getContent().stream()
                .map(roleMapper::entityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(roleResponseList, pageRequest, page.getTotalElements());
    }

    public RoleResponse getOne(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Role not found with id: "));
        return roleMapper.entityToResponse(role);
    }

    public RoleResponse create(RoleRequest request) {
        Role created = roleRepository.save(roleMapper.requestToEntity(request));
        return roleMapper.entityToResponse(created);
    }

    public RoleResponse update(RoleRequest request, Long id) {
        Role entity = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Role not found with id: "));
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new NotFoundException(request.getModuleId(), "Module not found with id: "));
        entity.setModule(module);
        entity.setLibelle(request.getLibelle());

        return roleMapper.entityToResponse(entity);
    }

    public void delete(Long id) {
        Role entity = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Role not found with id: "));
        roleRepository.delete(entity);
    }

    public RoleResponse addAuthorityToRole(Long roleId, Long authorityId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(roleId, "Role not found with id: "));
        Authority authority = authorityRepository.findById(authorityId)
                .orElseThrow(() -> new NotFoundException(authorityId, "Authority not found with id: "));
        if (role.getModule() != authority.getModule()) {
            throw new NotMatchException(role.getModule().getModuleName(),
                    role.getModule().getId(),
                    authority.getModule().getModuleName(),
                    authority.getModule().getId());
        }
        role.addAuthority(authority);
        return roleMapper.entityToResponse(role);
    }

    public RoleResponse removeAuthorityFromRole(Long roleId, Long authorityId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(roleId, "Role not found with id: "));
        Authority authority = authorityRepository.findById(authorityId)
                .orElseThrow(() -> new NotFoundException(authorityId, "Authority not found with id: "));
        if (!role.getAuthorities().contains(authority)) {
            throw new NotFoundException("Authority not found with id: " + authorityId + "in role with id: " + roleId);
        }
        role.removeAuthority(authority);
        return roleMapper.entityToResponse(role);
    }
}
