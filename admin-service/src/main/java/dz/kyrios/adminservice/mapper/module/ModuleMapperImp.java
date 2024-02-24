package dz.kyrios.adminservice.mapper.module;

import dz.kyrios.adminservice.dto.module.ModuleRequest;
import dz.kyrios.adminservice.dto.module.ModuleResponse;
import dz.kyrios.adminservice.entity.Module;
import org.springframework.stereotype.Component;

@Component
public class ModuleMapperImp implements ModuleMapper{

    @Override
    public Module requestToEntity(ModuleRequest request) {
        return Module.builder()
                .id(request.getId())
                .moduleName(request.getModuleName())
                .moduleCode(request.getModuleCode())
                .color(request.getColor())
                .icon(request.getIcon())
                .uri(request.getUri())
                .actif(request.getActif())
                .build();
    }

    @Override
    public ModuleResponse entityToResponse(Module entity) {
        return ModuleResponse.builder()
                .id(entity.getId())
                .moduleName(entity.getModuleName())
                .moduleCode(entity.getModuleCode())
                .color(entity.getColor())
                .icon(entity.getIcon())
                .uri(entity.getUri())
                .actif(entity.getActif())
                .build();
    }
}
