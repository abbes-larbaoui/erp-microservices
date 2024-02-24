package dz.kyrios.adminservice.controller;

import dz.kyrios.adminservice.service.PermissionService;
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
    public Boolean getPermissionForUser(@RequestParam String authority, @RequestParam String module) {
        return permissionService.getPermissionForUser(authority, module);
    }
}
