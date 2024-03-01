package dz.kyrios.adminservice.controller;

import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.clause.ClauseOneArg;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.Critiria;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SearchValue;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SortParam;
import dz.kyrios.adminservice.dto.profile.ProfileRequest;
import dz.kyrios.adminservice.dto.user.UserCreateRequest;
import dz.kyrios.adminservice.dto.user.UserRequest;
import dz.kyrios.adminservice.dto.user.UserResponse;
import dz.kyrios.adminservice.enums.KeycloakRequiredAction;
import dz.kyrios.adminservice.service.UserService;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/v1/user")
    @PreAuthorize("hasAuthority('USER_LIST')")
    public PageImpl<UserResponse> getAllFilter(@SortParam PageRequest pageRequest,
                                               @Critiria List<Clause> filter,
                                               @SearchValue ClauseOneArg searchValue) {
        filter.add(searchValue);
        return userService.findAllFilter(pageRequest, filter);
    }

    @PostMapping("/api/v1/user/{profileTypeId}")
    @PreAuthorize("hasAuthority('USER_VIEW')")
    public ResponseEntity<UserResponse> create(@RequestBody UserCreateRequest request,
                                               @PathVariable Long profileTypeId) {
        UserResponse response = userService.create(request, profileTypeId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/v1/user/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/v1/user/{id}")
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public ResponseEntity<UserResponse> getOne(@PathVariable Long id) {
        UserResponse response = userService.getOne(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/v1/user/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResponseEntity<UserResponse> update(@RequestBody UserRequest request, @PathVariable Long id) {
        UserResponse response = userService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/v1/user/required-actions/{id}")
    @PreAuthorize("hasAuthority('USER_REQUIRED_ACTIONS_ADD')")
    public ResponseEntity<Object> addRequiredActions(@RequestBody KeycloakRequiredAction[] requiredActions,
                                                     @PathVariable Long id) {
        try {
            userService.userRequiredActions(id, Arrays.stream(requiredActions).map(Enum::toString).toArray(String[]::new));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/v1/user/enable-disable-user/{id}")
    @PreAuthorize("hasAuthority('USER_ENABLE_DISABLE')")
    public ResponseEntity<UserResponse> enableDisableUser(@RequestBody UserCreateRequest request,
                                                          @PathVariable Long id) {
        UserResponse response = userService.enableDisableUser(id, request.getActif());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/v1/user/profile/add-profile/{profileTypeId}")
    @PreAuthorize("hasAuthority('USER_PROFILE_ADD')")
    public ResponseEntity<UserResponse> addProfile(@RequestBody ProfileRequest request,
                                                   @PathVariable Long profileTypeId) {
        UserResponse response = userService.addProfile(request, profileTypeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/v1/user/profile/change-default-profile/{userId}/{profileId}")
    @PreAuthorize("hasAuthority('USER_ACTIF_PROFILE_CHANGE')")
    public ResponseEntity<UserResponse> changeActifProfile(@PathVariable Long userId,
                                                           @PathVariable Long profileId) {
        UserResponse response = userService.changeActifProfile(userId, profileId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/user/profile/{id}")
    @PreAuthorize("hasAuthority('USER_PROFILE_DELETE')")
    public ResponseEntity<Object> deleteProfile(@PathVariable Long id) {
        try {
            userService.deleteProfile(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/v1/user/profile/module/{profileId}/{moduleId}")
    @PreAuthorize("hasAuthority('USER_PROFILE_MODULE_ADD')")
    public ResponseEntity<UserResponse> addModuleToProfile(@PathVariable Long profileId,
                                                           @PathVariable Long moduleId) {
        UserResponse response = userService.addModuleToProfile(profileId, moduleId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/user/profile/module/{profileId}/{moduleId}")
    @PreAuthorize("hasAuthority('USER_PROFILE_MODULE_REMOVE')")
    public ResponseEntity<UserResponse> removeModuleFromProfile(@PathVariable Long profileId,
                                                                @PathVariable Long moduleId) {
        UserResponse response = userService.removeModuleFromProfile(profileId, moduleId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/v1/user/profile/role/{profileId}/{roleId}")
    @PreAuthorize("hasAuthority('USER_PROFILE_ROLE_ADD')")
    public ResponseEntity<UserResponse> addRoleToProfile(@PathVariable Long profileId,
                                                         @PathVariable Long roleId) {
        UserResponse response = userService.addRoleToProfile(profileId, roleId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/user/profile/role/{profileId}/{roleId}")
    @PreAuthorize("hasAuthority('USER_PROFILE_ROLE_REMOVE')")
    public ResponseEntity<UserResponse> removeRoleFromProfile(@PathVariable Long profileId,
                                                              @PathVariable Long roleId) {
        UserResponse response = userService.removeRoleFromProfile(profileId, roleId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/v1/user/profile/authority/{profileId}/{authorityId}")
    @PreAuthorize("hasAuthority('USER_PROFILE_AUTHORITY_ADD')")
    public ResponseEntity<Object> addAuthorityToProfile(@PathVariable Long profileId,
                                                              @PathVariable Long authorityId) {
        try {
            UserResponse response = userService.addAuthorityToProfile(profileId, authorityId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/v1/user/profile/authority/{profileId}/{authorityId}")
    @PreAuthorize("hasAuthority('USER_PROFILE_AUTHORITY_REMOVE')")
    public ResponseEntity<UserResponse> removeAuthorityFromProfile(@PathVariable Long profileId,
                                                                   @PathVariable Long authorityId) {
        UserResponse response = userService.removeAuthorityFromProfile(profileId, authorityId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
