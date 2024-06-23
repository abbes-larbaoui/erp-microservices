package dz.kyrios.adminservice.controller;

import dz.kyrios.adminservice.config.exception.NotFoundException;
import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.clause.ClauseOneArg;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.Critiria;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SearchValue;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SortParam;
import dz.kyrios.adminservice.dto.authoritytype.AuthorityTypeRequest;
import dz.kyrios.adminservice.dto.authoritytype.AuthorityTypeResponse;
import dz.kyrios.adminservice.service.AuthorityTypeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorityTypeController {
    private final AuthorityTypeService authorityTypeService;

    public AuthorityTypeController(AuthorityTypeService authorityTypeService) {
        this.authorityTypeService = authorityTypeService;
    }

    @GetMapping("/api/v1/authority-type")
    @PreAuthorize("hasAuthority('AUTHORITY_TYPE_LIST')")
    public ResponseEntity<Object> getAllFilter(@SortParam PageRequest pageRequest,
                                               @Critiria List<Clause> filter,
                                               @SearchValue ClauseOneArg searchValue) {
        filter.add(searchValue);
        try {
            return new ResponseEntity<>(authorityTypeService.findAllFilter(pageRequest, filter), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/v1/authority-type/{id}")
    @PreAuthorize("hasAuthority('AUTHORITY_TYPE_VIEW')")
    public ResponseEntity<Object> getOne(@PathVariable Long id) {
        try {
            AuthorityTypeResponse response = authorityTypeService.getOne(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/v1/authority-type")
//    @PreAuthorize("hasAuthority('AUTHORITY_TYPE_CREATE')")
    public ResponseEntity<Object> add(@RequestBody AuthorityTypeRequest request) {
        try {
            AuthorityTypeResponse response = authorityTypeService.create(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/v1/authority-type/{id}")
    @PreAuthorize("hasAuthority('AUTHORITY_TYPE_UPDATE')")
    public ResponseEntity<Object> update(@RequestBody AuthorityTypeRequest request,
                                         @PathVariable Long id) {
        try {
            AuthorityTypeResponse response = authorityTypeService.update(request, id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/v1/authority-type/{id}")
    @PreAuthorize("hasAuthority('AUTHORITY_TYPE_DELETE')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            authorityTypeService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
