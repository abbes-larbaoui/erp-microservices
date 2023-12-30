package dz.kyrios.adminservice.controller;

import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.clause.ClauseOneArg;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.Critiria;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SearchValue;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SortParam;
import dz.kyrios.adminservice.dto.group.GroupRequest;
import dz.kyrios.adminservice.dto.group.GroupResponse;
import dz.kyrios.adminservice.service.GroupService;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController {
    
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/api/v1/group")
    public PageImpl<GroupResponse> getAllFilter(@SortParam PageRequest pageRequest,
                                                @Critiria List<Clause> filter,
                                                @SearchValue ClauseOneArg searchValue) {
        filter.add(searchValue);
        return groupService.findAllFilter(pageRequest, filter);
    }

    @GetMapping("/api/v1/group/{id}")
    public ResponseEntity<GroupResponse> getOne(@PathVariable Long id) {
        GroupResponse response = groupService.getOne(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/v1/group")
    public ResponseEntity<GroupResponse> add(@RequestBody GroupRequest request) {
        GroupResponse response = groupService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/api/v1/group/{id}")
    public ResponseEntity<GroupResponse> update(@RequestBody GroupRequest request, @PathVariable Long id) {
        GroupResponse response = groupService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/group/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            groupService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
