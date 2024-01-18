package dz.kyrios.adminservice.service;

import dz.kyrios.adminservice.config.exception.NotFoundException;
import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.specification.GenericSpecification;
import dz.kyrios.adminservice.dto.profile.ProfileRequest;
import dz.kyrios.adminservice.dto.user.UserCreateRequest;
import dz.kyrios.adminservice.dto.user.UserRequest;
import dz.kyrios.adminservice.dto.user.UserResponse;
import dz.kyrios.adminservice.entity.Profile;
import dz.kyrios.adminservice.entity.User;
import dz.kyrios.adminservice.mapper.user.UserMapper;
import dz.kyrios.adminservice.repository.ProfileRepository;
import dz.kyrios.adminservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserService {

    private final KeycloakService keycloakService;

    private final UserRepository userRepository;

    private final ProfileRepository profileRepository;
    
    private final UserMapper userMapper;

    @Autowired
    public UserService(KeycloakService keycloakService,
                       UserRepository userRepository,
                       ProfileRepository profileRepository,
                       UserMapper userMapper) {
        this.keycloakService = keycloakService;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.userMapper = userMapper;
    }

    public PageImpl<UserResponse> findAllFilter(PageRequest pageRequest, List<Clause> filter) {

        Specification<User> specification = new GenericSpecification<>(filter);
        List<UserResponse> userResponseList;
        Page<User> page;
        page = userRepository.findAll(specification, pageRequest);

        userResponseList = page.getContent().stream()
                .map(userMapper::entityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(userResponseList, pageRequest, page.getTotalElements());
    }

    public UserResponse getOne(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "User not found with id: "));
        return userMapper.entityToResponse(user);
    }

    public UserResponse create(UserCreateRequest request, Long profileTypeId) {
        String uuid = keycloakService.createUser(request);
        if (uuid == null || uuid.isEmpty()) {
            throw new RuntimeException("Could not create keycloak User");
        }

        Profile defaultProfile;
        if (profileTypeId != -1) {
            Profile profileType = profileRepository.findById(profileTypeId)
                    .orElseThrow(() -> new NotFoundException(profileTypeId, "Profile not found with id: "));
            defaultProfile = Profile.builder()
                    .libelle(profileType.getLibelle())
                    .group(profileType.getGroup())
                    .roles(profileType.getRoles())
                    .authorities(profileType.getAuthorities())
                    .modules(profileType.getModules())
                    .build();
        } else {
            defaultProfile = Profile.builder()
                    .libelle("Default profile")
                    .build();
        }
        User user = userMapper.requestToEntity(request);
        user.setUuid(uuid);
        defaultProfile.setUser(user);
        user.setDefaultProfile(defaultProfile);
        user.addProfile(defaultProfile);

         // Save the Profile, which also saves the User due to cascading

        return userMapper.entityToResponse(userRepository.save(user));
    }

    public void delete(Long id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "User not found with id: "));
        if (keycloakService.deleteUser(entity.getUuid())) {
            userRepository.delete(entity);
        }
    }

    public UserResponse update(UserRequest request, Long id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "User not found with id: "));

        keycloakService.editUser(entity.getUuid(), request);
        entity.setEmail(request.getEmail());
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());

        return userMapper.entityToResponse(entity);
    }

    public void userRequiredActions(Long id, String[] requiredActions) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "User not found with id: "));

        keycloakService.userRequiredAction(entity.getUuid(), requiredActions);
    }

    public UserResponse enableDisableUser(Long id, Boolean enable) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "User not found with id: "));

        keycloakService.enableDisableUser(entity.getUuid(), enable);
        entity.setActif(enable);

        return userMapper.entityToResponse(entity);
    }

    public UserResponse addProfile(ProfileRequest request, Long profileTypeId) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException(request.getUserId(), "User not found with id: "));

        Profile profileType = profileRepository.findById(profileTypeId)
                .orElseThrow(() -> new NotFoundException(profileTypeId, "Profile not found with id: "));

        Profile profile = Profile.builder()
                .user(user)
                .group(profileType.getGroup())
                .roles(profileType.getRoles())
                .modules(profileType.getModules())
                .authorities(profileType.getAuthorities())
                .libelle(request.getLibelle())
                .actif(request.getActif())
                .build();

        user.addProfile(profile);
        return userMapper.entityToResponse(user);
    }

    public UserResponse changeDefaultProfile(Long userId, Long profileId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(userId, "User not found with id: "));

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException(profileId, "Profile not found with id: "));

        if (!user.getProfiles().contains(profile)) {
            throw new NotFoundException("User have not that profile");
        }
        user.setDefaultProfile(profile);
        return userMapper.entityToResponse(user);
    }

    public void deleteProfile(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Profile not found with id: "));

        User user = userRepository.findById(profile.getUser().getId())
                .orElseThrow(() -> new NotFoundException(profile.getUser().getId(), "User not found with id: "));

        if (!user.getDefaultProfile().equals(profile)) {
            profileRepository.delete(profile);
        } else {
            throw new RuntimeException("Can not remove the default profile");
        }
    }
}
