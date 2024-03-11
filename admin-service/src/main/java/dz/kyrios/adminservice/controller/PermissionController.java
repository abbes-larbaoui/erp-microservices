package dz.kyrios.adminservice.controller;

import dz.kyrios.adminservice.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/api/v1/permission")
    public ResponseEntity<Object> getPermissionForUser(@RequestParam String authority, @RequestParam String module) {
        try {
            return new ResponseEntity<>(permissionService.getPermissionForUser(authority, module), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
