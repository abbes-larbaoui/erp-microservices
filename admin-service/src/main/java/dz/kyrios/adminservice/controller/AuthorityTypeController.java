package dz.kyrios.adminservice.controller;

import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.clause.ClauseOneArg;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.Critiria;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SearchValue;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SortParam;
import dz.kyrios.adminservice.dto.authoritytype.AuthorityTypeRequest;
import dz.kyrios.adminservice.dto.authoritytype.AuthorityTypeResponse;
import dz.kyrios.adminservice.service.AuthorityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorityTypeController {
    private final AuthorityTypeService authorityTypeService;

    @Autowired
    public AuthorityTypeController(AuthorityTypeService authorityTypeService) {
        this.authorityTypeService = authorityTypeService;
    }

    @GetMapping("/api/v1/authority-type")
    @PreAuthorize("hasAuthority('AUTHORITY_TYPE_LIST')")
    public PageImpl<AuthorityTypeResponse> getAllFilter(@SortParam PageRequest pageRequest,
                                                        @Critiria List<Clause> filter,
                                                        @SearchValue ClauseOneArg searchValue) {
        filter.add(searchValue);
        return authorityTypeService.findAllFilter(pageRequest, filter);
    }

    @GetMapping("/api/v1/authority-type/{id}")
    @PreAuthorize("hasAuthority('AUTHORITY_TYPE_VIEW')")
    public ResponseEntity<AuthorityTypeResponse> getOne(@PathVariable Long id) {
        AuthorityTypeResponse response = authorityTypeService.getOne(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/v1/authority-type")
//    @PreAuthorize("hasAuthority('AUTHORITY_TYPE_CREATE')")
    public ResponseEntity<AuthorityTypeResponse> add(@RequestBody AuthorityTypeRequest request) {
        AuthorityTypeResponse response = authorityTypeService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/api/v1/authority-type/{id}")
    @PreAuthorize("hasAuthority('AUTHORITY_TYPE_UPDATE')")
    public ResponseEntity<AuthorityTypeResponse> update(@RequestBody AuthorityTypeRequest request,@PathVariable Long id) {
        AuthorityTypeResponse response = authorityTypeService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/authority-type/{id}")
    @PreAuthorize("hasAuthority('AUTHORITY_TYPE_DELETE')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            authorityTypeService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
