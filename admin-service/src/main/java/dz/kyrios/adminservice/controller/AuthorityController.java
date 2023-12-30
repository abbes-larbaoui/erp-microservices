package dz.kyrios.adminservice.controller;

import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.clause.ClauseOneArg;
import dz.kyrios.adminservice.config.filter.enums.Operation;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.Critiria;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SearchValue;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SortParam;
import dz.kyrios.adminservice.dto.authority.AuthorityRequest;
import dz.kyrios.adminservice.dto.authority.AuthorityResponse;
import dz.kyrios.adminservice.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorityController {
    
    private final AuthorityService authorityService;

    @Autowired
    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping("/api/v1/authority")
    public PageImpl<AuthorityResponse> getAllFilter(@SortParam PageRequest pageRequest,
                                                    @Critiria List<Clause> filter,
                                                    @SearchValue ClauseOneArg searchValue) {
        filter.add(searchValue);
        return authorityService.findAllFilter(pageRequest, filter);
    }

    @GetMapping("/api/v1/authority/module/{moduleId}")
    public PageImpl<AuthorityResponse> getAllByModuleFilter(@SortParam PageRequest pageRequest,
                                                            @Critiria List<Clause> filter,
                                                            @SearchValue ClauseOneArg searchValue,
                                                            @PathVariable Long moduleId) {
        filter.add(new ClauseOneArg("module.id", Operation.Equals, moduleId.toString()));
        filter.add(searchValue);
        return authorityService.findAllFilter(pageRequest, filter);
    }

    @GetMapping("/api/v1/authority/{id}")
    public ResponseEntity<AuthorityResponse> getOne(@PathVariable Long id) {
        AuthorityResponse response = authorityService.getOne(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/v1/authority")
    public ResponseEntity<AuthorityResponse> add(@RequestBody AuthorityRequest request) {
        AuthorityResponse response = authorityService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/api/v1/authority/{id}")
    public ResponseEntity<AuthorityResponse> update(@RequestBody AuthorityRequest request, @PathVariable Long id) {
        AuthorityResponse response = authorityService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/authority/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            authorityService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
