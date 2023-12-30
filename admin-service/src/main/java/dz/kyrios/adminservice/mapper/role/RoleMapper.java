package dz.kyrios.adminservice.mapper.role;

import dz.kyrios.adminservice.dto.role.RoleRequest;
import dz.kyrios.adminservice.dto.role.RoleResponse;
import dz.kyrios.adminservice.entity.Role;

public interface RoleMapper {

    Role requestToEntity(RoleRequest request);

    RoleResponse entityToResponse(Role entity);
}
