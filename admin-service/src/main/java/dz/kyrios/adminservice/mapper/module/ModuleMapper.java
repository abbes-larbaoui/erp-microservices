package dz.kyrios.adminservice.mapper.module;

import dz.kyrios.adminservice.dto.module.ModuleRequest;
import dz.kyrios.adminservice.dto.module.ModuleResponse;
import dz.kyrios.adminservice.entity.Module;

public interface ModuleMapper {

    Module requestToEntity(ModuleRequest request);

    ModuleResponse entityToResponse(Module entity);
}
