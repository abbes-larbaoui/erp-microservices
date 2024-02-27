package dz.kyrios.adminservice.controller;

import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.clause.ClauseOneArg;
import dz.kyrios.adminservice.config.filter.enums.Operation;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.Critiria;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SearchValue;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SortParam;
import dz.kyrios.adminservice.dto.authority.AuthorityRequest;
import dz.kyrios.adminservice.dto.role.RoleRequest;
import dz.kyrios.adminservice.dto.role.RoleResponse;
import dz.kyrios.adminservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/api/v1/role/module/{moduleId}")
    @PreAuthorize("hasAuthority('ROLE_LIST_MODULE')")
    public PageImpl<RoleResponse> getAllFilter(@SortParam PageRequest pageRequest,
                                               @Critiria List<Clause> filter,
                                               @SearchValue ClauseOneArg searchValue,
                                               @PathVariable Long moduleId) {
        filter.add(new ClauseOneArg("module.id", Operation.Equals, moduleId.toString()));
        filter.add(searchValue);
        return roleService.findAllFilterByModule(pageRequest, filter);
    }

    @PostMapping("/api/v1/role")
    @PreAuthorize("hasAuthority('ROLE_VIEW')")
    public ResponseEntity<RoleResponse> add(@RequestBody RoleRequest request) {
        RoleResponse response = roleService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/role/{id}")
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public ResponseEntity<RoleResponse> getOne(@PathVariable Long id) {
        RoleResponse response = roleService.getOne(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/api/v1/role/{id}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public ResponseEntity<RoleResponse> update(@RequestBody RoleRequest request, @PathVariable Long id) {
        RoleResponse response = roleService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/role/{id}")
    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            roleService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/v1/role/authority-add/{roleId}")
    @PreAuthorize("hasAuthority('ROLE_AUTHORITY_ADD')")
    public ResponseEntity<RoleResponse> addAuthorityToRole(@RequestBody AuthorityRequest request, @PathVariable Long roleId) {
        RoleResponse response = roleService.addAuthorityToRole(roleId, request.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/v1/role/authority-remove/{roleId}")
    @PreAuthorize("hasAuthority('ROLE_AUTHORITY_REMOVE')")
    public ResponseEntity<RoleResponse> removeAuthorityFromRole(@RequestBody AuthorityRequest request, @PathVariable Long roleId) {
        RoleResponse response = roleService.removeAuthorityFromRole(roleId, request.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
