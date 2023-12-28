package dz.kyrios.adminservice.controller;

import dz.kyrios.adminservice.config.filter.clause.Clause;
import dz.kyrios.adminservice.config.filter.clause.ClauseOneArg;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.Critiria;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SearchValue;
import dz.kyrios.adminservice.config.filter.handlerMethodArgumentResolver.SortParam;
import dz.kyrios.adminservice.dto.module.ModuleRequest;
import dz.kyrios.adminservice.dto.module.ModuleResponse;
import dz.kyrios.adminservice.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ModuleController {

    private final ModuleService moduleService;

    @Autowired
    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping("/api/v1/module")
    public PageImpl<ModuleResponse> getAllFilter(@SortParam PageRequest pageRequest,
                                                 @Critiria List<Clause> filter,
                                                 @SearchValue ClauseOneArg searchValue) {
        filter.add(searchValue);
        return moduleService.findAllFilter(pageRequest, filter);
    }

    @GetMapping("/api/v1/module/{id}")
    public ResponseEntity<ModuleResponse> getOne(@PathVariable Long id) {
        ModuleResponse response = moduleService.getOne(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/v1/module")
    public ResponseEntity<ModuleResponse> add(@RequestBody ModuleRequest request) {
        ModuleResponse response = moduleService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/api/v1/module/{id}")
    public ResponseEntity<ModuleResponse> update(@RequestBody ModuleRequest request,@PathVariable Long id) {
        ModuleResponse response = moduleService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/module/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            moduleService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
