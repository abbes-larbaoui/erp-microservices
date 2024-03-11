package dz.kyrios.adminservice.controller;

import dz.kyrios.adminservice.config.exception.NotFoundException;
import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.clause.ClauseOneArg;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.Critiria;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SearchValue;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SortParam;
import dz.kyrios.adminservice.dto.group.GroupRequest;
import dz.kyrios.adminservice.dto.group.GroupResponse;
import dz.kyrios.adminservice.service.GroupService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/api/v1/group")
    @PreAuthorize("hasAuthority('GROUP_LIST')")
    public ResponseEntity<Object> getAllFilter(@SortParam PageRequest pageRequest,
                                               @Critiria List<Clause> filter,
                                               @SearchValue ClauseOneArg searchValue) {
        try {
            filter.add(searchValue);
            return new ResponseEntity<>(groupService.findAllFilter(pageRequest, filter), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/v1/group/{id}")
    @PreAuthorize("hasAuthority('GROUP_VIEW')")
    public ResponseEntity<Object> getOne(@PathVariable Long id) {
        try {
            GroupResponse response = groupService.getOne(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/v1/group")
    @PreAuthorize("hasAuthority('GROUP_CREATE')")
    public ResponseEntity<Object> add(@RequestBody GroupRequest request) {
        try {
            GroupResponse response = groupService.create(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/v1/group/{id}")
    @PreAuthorize("hasAuthority('GROUP_UPDATE')")
    public ResponseEntity<Object> update(@RequestBody GroupRequest request,
                                         @PathVariable Long id) {
        try {
            GroupResponse response = groupService.update(request, id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/v1/group/{id}")
    @PreAuthorize("hasAuthority('GROUP_DELETE')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            groupService.delete(id);
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
