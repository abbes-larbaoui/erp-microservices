package dz.kyrios.adminservice.mapper.profile;

import dz.kyrios.adminservice.config.exception.NotFoundException;
import dz.kyrios.adminservice.dto.authority.AuthorityResponse;
import dz.kyrios.adminservice.dto.module.ModuleResponse;
import dz.kyrios.adminservice.dto.profile.ProfileAuthorityResponse;
import dz.kyrios.adminservice.dto.profile.ProfileRequest;
import dz.kyrios.adminservice.dto.profile.ProfileResponse;
import dz.kyrios.adminservice.dto.role.RoleResponse;
import dz.kyrios.adminservice.entity.Group;
import dz.kyrios.adminservice.entity.Profile;
import dz.kyrios.adminservice.entity.ProfileAuthority;
import dz.kyrios.adminservice.entity.User;
import dz.kyrios.adminservice.mapper.authority.AuthorityMapper;
import dz.kyrios.adminservice.mapper.group.GroupMapper;
import dz.kyrios.adminservice.mapper.module.ModuleMapper;
import dz.kyrios.adminservice.mapper.role.RoleMapper;
import dz.kyrios.adminservice.mapper.user.UserMapper;
import dz.kyrios.adminservice.repository.GroupRepository;
import dz.kyrios.adminservice.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ProfileMapperImp implements ProfileMapper{

    private final UserRepository userRepository;

    private final GroupRepository groupRepository;

    private final UserMapper userMapper;

    private final GroupMapper groupMapper;

    private final RoleMapper roleMapper;

    private final ModuleMapper moduleMapper;

    private final AuthorityMapper authorityMapper;

    public ProfileMapperImp(UserRepository userRepository,
                            GroupRepository groupRepository,
                            UserMapper userMapper,
                            GroupMapper groupMapper,
                            RoleMapper roleMapper,
                            ModuleMapper moduleMapper,
                            AuthorityMapper authorityMapper) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.userMapper = userMapper;
        this.groupMapper = groupMapper;
        this.roleMapper = roleMapper;
        this.moduleMapper = moduleMapper;
        this.authorityMapper = authorityMapper;
    }

    @Override
    public Profile requestToEntity(ProfileRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException(request.getUserId(), "User not found with id: "));
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new NotFoundException(request.getGroupId(), "Group not found with id: "));
        return Profile.builder()
                .libelle(request.getLibelle())
                .user(user)
                .group(group)
                .actif(request.getActif())
                .build();
    }

    @Override
    public ProfileResponse entityToResponse(Profile entity) {
        Set<ModuleResponse> moduleResponses = new HashSet<>();
        if(entity.getModules() != null && !entity.getModules().isEmpty()) {
            entity.getModules().forEach(module -> moduleResponses.add(moduleMapper.entityToResponse(module)));
        }
        Set<RoleResponse> roleResponses = new HashSet<>();
        if(entity.getRoles() != null && !entity.getRoles().isEmpty()) {
            entity.getRoles().forEach(role -> roleResponses.add(roleMapper.entityToResponse(role)));
        }
        Set<ProfileAuthorityResponse> authorityResponses = new HashSet<>();
        if(entity.getAuthorities() != null && !entity.getAuthorities().isEmpty()) {
            entity.getAuthorities().forEach(authority -> authorityResponses.add(profileAuthorityToResponse(authority)));
        }
        return ProfileResponse.builder()
                .id(entity.getId())
                .libelle(entity.getLibelle())
                .userResponse(userMapper.entityToResponse(entity.getUser()))
                .groupResponse(groupMapper.entityToResponse(entity.getGroup()))
                .authorityResponses(authorityResponses)
                .roleResponses(roleResponses)
                .moduleResponses(moduleResponses)
                .actif(entity.getActif())
                .build();
    }

    private ProfileAuthorityResponse profileAuthorityToResponse(ProfileAuthority entity) {
        return ProfileAuthorityResponse.builder()
                .id(entity.getId())
                .authority(authorityMapper.entityToResponse(entity.getAuthority()))
                .granted(entity.getGranted())
                .build();
    }
}
