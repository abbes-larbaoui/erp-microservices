package dz.kyrios.adminservice.controller;

import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.clause.ClauseOneArg;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.Critiria;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SearchValue;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SortParam;
import dz.kyrios.adminservice.dto.role.RoleRequest;
import dz.kyrios.adminservice.dto.role.RoleResponse;
import dz.kyrios.adminservice.dto.user.UserCreateRequest;
import dz.kyrios.adminservice.dto.user.UserRequest;
import dz.kyrios.adminservice.dto.user.UserResponse;
import dz.kyrios.adminservice.enums.KeycloakRequiredAction;
import dz.kyrios.adminservice.service.UserService;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public PageImpl<UserResponse> getAllFilter(@SortParam PageRequest pageRequest,
                                               @Critiria List<Clause> filter,
                                               @SearchValue ClauseOneArg searchValue) {
        filter.add(searchValue);
        return userService.findAllFilter(pageRequest, filter);
    }

    @PostMapping("/api/v1/user/{profileTypeId}")
    public ResponseEntity<UserResponse> create(@RequestBody UserCreateRequest request,
                                               @PathVariable Long profileTypeId) {
        UserResponse response = userService.create(request, profileTypeId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/v1/user/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/v1/user/{id}")
    public ResponseEntity<UserResponse> getOne(@PathVariable Long id) {
        UserResponse response = userService.getOne(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/v1/user/{id}")
    public ResponseEntity<UserResponse> update(@RequestBody UserRequest request, @PathVariable Long id) {
        UserResponse response = userService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/v1/user/required-actions/{id}")
    public ResponseEntity<Object> addRequiredActions(@RequestBody KeycloakRequiredAction[] requiredActions,
                                               @PathVariable Long id) {
        try {
            userService.userRequiredActions(id, Arrays.stream(requiredActions).map(Enum::toString).toArray(String[]::new));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
