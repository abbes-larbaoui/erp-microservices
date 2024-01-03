package dz.kyrios.adminservice.controller;

import dz.kyrios.adminservice.dto.role.RoleRequest;
import dz.kyrios.adminservice.dto.role.RoleResponse;
import dz.kyrios.adminservice.dto.user.UserCreateRequest;
import dz.kyrios.adminservice.dto.user.UserResponse;
import dz.kyrios.adminservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/v1/user/{profileTypeId}")
    public ResponseEntity<UserResponse> create(@RequestBody UserCreateRequest request,
                                               @PathVariable Long profileTypeId) {
        UserResponse response = userService.create(request, profileTypeId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
