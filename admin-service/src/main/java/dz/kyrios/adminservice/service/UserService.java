package dz.kyrios.adminservice.service;

import dz.kyrios.adminservice.config.exception.NotFoundException;
import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.specification.GenericSpecification;
import dz.kyrios.adminservice.dto.profile.ProfileRequest;
import dz.kyrios.adminservice.dto.user.UserCreateRequest;
import dz.kyrios.adminservice.dto.user.UserRequest;
import dz.kyrios.adminservice.dto.user.UserResponse;
import dz.kyrios.adminservice.dto.user.UserSessionResponse;
import dz.kyrios.adminservice.entity.*;
import dz.kyrios.adminservice.entity.Module;
import dz.kyrios.adminservice.enums.NotificationChannel;
import dz.kyrios.adminservice.enums.NotificationTemplateCode;
import dz.kyrios.adminservice.event.notification.NotificationPayload;
import dz.kyrios.adminservice.event.user.UserCreatedEvent;
import dz.kyrios.adminservice.mapper.user.UserMapper;
import dz.kyrios.adminservice.mapper.usersession.UserSessionMapper;
import dz.kyrios.adminservice.repository.*;
import org.keycloak.representations.idm.UserSessionRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserService {

    private final KeycloakService keycloakService;

    private final UserRepository userRepository;

    private final ProfileRepository profileRepository;

    private final ModuleRepository moduleRepository;

    private final AuthorityRepository authorityRepository;

    private final ProfileAuthorityRepository profileAuthorityRepository;

    private final RoleRepository roleRepository;
    
    private final UserMapper userMapper;

    private final UserSessionMapper userSessionMapper;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UserService(KeycloakService keycloakService,
                       UserRepository userRepository,
                       ProfileRepository profileRepository,
                       ModuleRepository moduleRepository,
                       AuthorityRepository authorityRepository,
                       ProfileAuthorityRepository profileAuthorityRepository,
                       RoleRepository roleRepository,
                       UserMapper userMapper,
                       UserSessionMapper userSessionMapper,
                       KafkaTemplate<String, Object> kafkaTemplate) {
        this.keycloakService = keycloakService;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.moduleRepository = moduleRepository;
        this.authorityRepository = authorityRepository;
        this.profileAuthorityRepository = profileAuthorityRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.userSessionMapper = userSessionMapper;
        this.kafkaTemplate = kafkaTemplate;
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

    public User getOneByUsername(String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new NotFoundException("User not found with username: " + username));
        return user;
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
        user.setActifProfile(defaultProfile);
        user.addProfile(defaultProfile);

         // Save the Profile, which also saves the User due to cascading
        User createdUser = userRepository.save(user);
        UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder()
                .uuid(createdUser.getUuid())
                .userName(createdUser.getUserName())
                .email(createdUser.getEmail())
                .phoneNumber(createdUser.getPhoneNumber())
                .firstName(createdUser.getFirstName())
                .lastName(createdUser.getLastName())
                .build();
        // create user in microservices that needs
        kafkaTemplate.send("userCreatedTopic", userCreatedEvent);

        // notify user
        notifyUser(createdUser);

        return userMapper.entityToResponse(createdUser);
    }

    public void notifyUser(User user) {
        NotificationPayload payload = new NotificationPayload();
        payload.setUserUuid(user.getUuid());
        payload.setChannel(NotificationChannel.EMAIL);
        payload.setTemplateCode(NotificationTemplateCode.USER_CREATED_EMAIL);
//        payload.setSubjectPlaceHolders();
//        payload.setBodyPlaceHolders();
        payload.setTime(LocalDateTime.now());
        kafkaTemplate.send("notificationTopic", payload);
    }

    public void delete(Long id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "User not found with id: "));
        if (keycloakService.deleteUser(entity.getUuid())) {
            userRepository.delete(entity);

            // TODO: send event about deleteing the user
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

    public UserResponse changeActifProfile(Long userId, Long profileId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(userId, "User not found with id: "));

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException(profileId, "Profile not found with id: "));

        if (!user.getProfiles().contains(profile)) {
            throw new NotFoundException("User have not that profile");
        }
        user.setActifProfile(profile);
        return userMapper.entityToResponse(user);
    }

    public void deleteProfile(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Profile not found with id: "));

        User user = userRepository.findById(profile.getUser().getId())
                .orElseThrow(() -> new NotFoundException(profile.getUser().getId(), "User not found with id: "));

        if (!user.getActifProfile().equals(profile)) {
            profileRepository.delete(profile);
        } else {
            throw new RuntimeException("Can not remove the default profile");
        }
    }

    public UserResponse addModuleToProfile(Long profileId, Long moduleId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException(profileId, "Profile not found with id: "));

        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new NotFoundException(moduleId, "Module not found with id: "));

        profile.addModule(module);
        return userMapper.entityToResponse(profile.getUser());
    }

    public UserResponse removeModuleFromProfile(Long profileId, Long moduleId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException(profileId, "Profile not found with id: "));

        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new NotFoundException(moduleId, "Module not found with id: "));

        profile.removeModule(module);
        return userMapper.entityToResponse(profile.getUser());
    }

    public UserResponse addRoleToProfile(Long profileId, Long roleId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException(profileId, "Profile not found with id: "));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(roleId, "Role not found with id: "));

        profile.addRole(role);
        return userMapper.entityToResponse(profile.getUser());
    }

    public UserResponse removeRoleFromProfile(Long profileId, Long roleId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException(profileId, "Profile not found with id: "));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(roleId, "Role not found with id: "));

        profile.removeRole(role);
        return userMapper.entityToResponse(profile.getUser());
    }

//    public UserResponse addAuthorityToProfile(Long profileId, Long authorityId) {
//        Profile profile = profileRepository.findById(profileId)
//                .orElseThrow(() -> new NotFoundException(profileId, "Profile not found with id: "));
//
//        Authority authority = authorityRepository.findById(authorityId)
//                .orElseThrow(() -> new NotFoundException(authorityId, "Authority not found with id: "));
//
//        if (profile.getModules().contains(authority.getModule())) {
//            profile.addAuthority(authority);
//        } else {
//            throw new RuntimeException("this profile of user haven't the access to the module of the authority");
//        }
//
//        return userMapper.entityToResponse(profile.getUser());
//    }

    public List<UserSessionResponse> getUsersSessions(Integer first, Integer max) {
        List<UserSessionResponse> responses = new ArrayList<>();
        List<UserSessionRepresentation> keycloakSessions = keycloakService.getUsersSessions(first, max);

        keycloakSessions.forEach(userSessionRepresentation -> {

            UserSessionResponse userSessionResponse = userSessionMapper.keycloakSessionToUserSessionResponse(userSessionRepresentation);
            userSessionResponse.setUser(userMapper.entityToResponse(getOneByUsername(userSessionRepresentation.getUsername())));

            responses.add(userSessionResponse);
        });

        return responses;
    }


    public UserResponse grantAuthorityToProfile(Long profileId, Long authorityId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException(profileId, "Profile not found with id: "));

        Authority authority = authorityRepository.findById(authorityId)
                .orElseThrow(() -> new NotFoundException(authorityId, "Authority not found with id: "));

        if (profile.getModules().contains(authority.getModule())) {
            /* check if authority exists in profile authorities */
            if (profile.getAuthorities().stream().anyMatch(profileAuthority -> profileAuthority.getAuthority().equals(authority))) {
                profile.getAuthorities().stream()
                        .filter(profileAuthority -> profileAuthority.getAuthority().getId().equals(authority.getId())
                                && profileAuthority.getProfile().getId().equals(profile.getId()))
                        .forEach(profileAuthority -> profileAuthority.setGranted(Boolean.TRUE));
            } else {
                ProfileAuthority profileAuthority = ProfileAuthority.builder()
                        .authority(authority)
                        .profile(profile)
                        .granted(Boolean.TRUE)
                        .build();
                profile.addAuthority(profileAuthority);
            }
        } else {
            throw new RuntimeException("this profile of user haven't the access to the module of the authority");
        }

        return userMapper.entityToResponse(profile.getUser());
    }

    public UserResponse revokeAuthorityToProfile(Long profileId, Long authorityId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException(profileId, "Profile not found with id: "));

        Authority authority = authorityRepository.findById(authorityId)
                .orElseThrow(() -> new NotFoundException(authorityId, "Authority not found with id: "));

        if (profile.getModules().contains(authority.getModule())) {
            /* check if authority exists in profile authorities */
            if (profile.getAuthorities().stream().anyMatch(profileAuthority -> profileAuthority.getAuthority().equals(authority))) {
                profile.getAuthorities().stream()
                        .filter(profileAuthority -> profileAuthority.getAuthority().getId().equals(authority.getId())
                                && profileAuthority.getProfile().getId().equals(profile.getId()))
                        .forEach(profileAuthority -> profileAuthority.setGranted(Boolean.FALSE));
            } else {
                ProfileAuthority profileAuthority = ProfileAuthority.builder()
                        .authority(authority)
                        .profile(profile)
                        .granted(Boolean.FALSE)
                        .build();
                profile.addAuthority(profileAuthority);
            }
        } else {
            throw new RuntimeException("this profile of user haven't the access to the module of the authority");
        }

        return userMapper.entityToResponse(profile.getUser());
    }

    public UserResponse removeAuthorityFromProfile(Long profileId, Long authorityId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException(profileId, "Profile not found with id: "));

        ProfileAuthority profileAuthority = profileAuthorityRepository.findProfileAuthorityByProfile_IdAndAuthority_Id(profileId, authorityId)
                .orElseThrow(() -> new NotFoundException("Authority not found with id: " + authorityId + "in Profile with id: " + profileId));

        if (profile.getAuthorities() != null) {
            profile.getAuthorities().removeIf(profileAuthority1 -> profileAuthority1.equals(profileAuthority));
        }
        return userMapper.entityToResponse(profile.getUser());
    }
}
