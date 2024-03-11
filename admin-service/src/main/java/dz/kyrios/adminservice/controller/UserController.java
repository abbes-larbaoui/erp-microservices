package dz.kyrios.adminservice.controller;

import dz.kyrios.adminservice.config.exception.NotFoundException;
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
    public ResponseEntity<Object> getAllFilter(@SortParam PageRequest pageRequest,
                                               @Critiria List<Clause> filter,
                                               @SearchValue ClauseOneArg searchValue) {
        try {
            filter.add(searchValue);
            return new ResponseEntity<>(userService.findAllFilter(pageRequest, filter), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/v1/user/{profileTypeId}")
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public ResponseEntity<Object> create(@RequestBody UserCreateRequest request,
                                               @PathVariable Long profileTypeId) {
        try {
            UserResponse response = userService.create(request, profileTypeId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/v1/user/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/v1/user/{id}")
    @PreAuthorize("hasAuthority('USER_VIEW')")
    public ResponseEntity<Object> getOne(@PathVariable Long id) {
        try {
            UserResponse response = userService.getOne(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/v1/user/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResponseEntity<Object> update(@RequestBody UserRequest request, @PathVariable Long id) {
        try {
            UserResponse response = userService.update(request, id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/v1/user/required-actions/{id}")
    @PreAuthorize("hasAuthority('USER_REQUIRED_ACTIONS_ADD')")
    public ResponseEntity<Object> addRequiredActions(@RequestBody KeycloakRequiredAction[] requiredActions,
                                                     @PathVariable Long id) {
        try {
            userService.userRequiredActions(id, Arrays.stream(requiredActions).map(Enum::toString).toArray(String[]::new));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/v1/user/enable-disable-user/{id}")
    @PreAuthorize("hasAuthority('USER_ENABLE_DISABLE')")
    public ResponseEntity<Object> enableDisableUser(@RequestBody UserCreateRequest request,
                                                    @PathVariable Long id) {
        try {
            UserResponse response = userService.enableDisableUser(id, request.getActif());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/v1/user/profile/add-profile/{profileTypeId}")
    @PreAuthorize("hasAuthority('USER_PROFILE_ADD')")
    public ResponseEntity<Object> addProfile(@RequestBody ProfileRequest request,
                                             @PathVariable Long profileTypeId) {
        try {
            UserResponse response = userService.addProfile(request, profileTypeId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/v1/user/profile/change-default-profile/{userId}/{profileId}")
    @PreAuthorize("hasAuthority('USER_ACTIF_PROFILE_CHANGE')")
    public ResponseEntity<Object> changeActifProfile(@PathVariable Long userId,
                                                     @PathVariable Long profileId) {
        try {
            UserResponse response = userService.changeActifProfile(userId, profileId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/v1/user/profile/{id}")
    @PreAuthorize("hasAuthority('USER_PROFILE_DELETE')")
    public ResponseEntity<Object> deleteProfile(@PathVariable Long id) {
        try {
            userService.deleteProfile(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/v1/user/profile/module/{profileId}/{moduleId}")
    @PreAuthorize("hasAuthority('USER_PROFILE_MODULE_ADD')")
    public ResponseEntity<Object> addModuleToProfile(@PathVariable Long profileId,
                                                     @PathVariable Long moduleId) {
        try {
            UserResponse response = userService.addModuleToProfile(profileId, moduleId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/v1/user/profile/module/{profileId}/{moduleId}")
    @PreAuthorize("hasAuthority('USER_PROFILE_MODULE_REMOVE')")
    public ResponseEntity<Object> removeModuleFromProfile(@PathVariable Long profileId,
                                                          @PathVariable Long moduleId) {
        try {
            UserResponse response = userService.removeModuleFromProfile(profileId, moduleId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/v1/user/profile/role/{profileId}/{roleId}")
    @PreAuthorize("hasAuthority('USER_PROFILE_ROLE_ADD')")
    public ResponseEntity<Object> addRoleToProfile(@PathVariable Long profileId,
                                                   @PathVariable Long roleId) {
        try {
            UserResponse response = userService.addRoleToProfile(profileId, roleId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/v1/user/profile/role/{profileId}/{roleId}")
    @PreAuthorize("hasAuthority('USER_PROFILE_ROLE_REMOVE')")
    public ResponseEntity<Object> removeRoleFromProfile(@PathVariable Long profileId,
                                                        @PathVariable Long roleId) {
        try {
            UserResponse response = userService.removeRoleFromProfile(profileId, roleId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/v1/user/profile/authority/grant/{profileId}/{authorityId}")
    @PreAuthorize("hasAuthority('USER_PROFILE_AUTHORITY_GRANT')")
    public ResponseEntity<Object> grantAuthorityToProfile(@PathVariable Long profileId,
                                                          @PathVariable Long authorityId) {
        try {
            UserResponse response = userService.grantAuthorityToProfile(profileId, authorityId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/v1/user/profile/authority/revoke/{profileId}/{authorityId}")
    @PreAuthorize("hasAuthority('USER_PROFILE_AUTHORITY_REVOKE')")
    public ResponseEntity<Object> revokeAuthorityToProfile(@PathVariable Long profileId,
                                                           @PathVariable Long authorityId) {
        try {
            UserResponse response = userService.revokeAuthorityToProfile(profileId, authorityId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/v1/user/profile/authority/{profileId}/{authorityId}")
    @PreAuthorize("hasAuthority('USER_PROFILE_AUTHORITY_REMOVE')")
    public ResponseEntity<Object> removeAuthorityFromProfile(@PathVariable Long profileId,
                                                             @PathVariable Long authorityId) {
        try {
            UserResponse response = userService.removeAuthorityFromProfile(profileId, authorityId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/api/v1/user/sessions")
//    @PreAuthorize("hasAuthority('USER_SESSION_LIST')")
    public ResponseEntity<Object> getUsersSessions(@RequestParam Integer first,
                                                   @RequestParam Integer max) {
        try {
            return new ResponseEntity<>(userService.getUsersSessions(first, max), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
