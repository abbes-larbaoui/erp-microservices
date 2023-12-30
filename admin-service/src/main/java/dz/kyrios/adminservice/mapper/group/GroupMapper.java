package dz.kyrios.adminservice.mapper.group;

import dz.kyrios.adminservice.dto.group.GroupRequest;
import dz.kyrios.adminservice.dto.group.GroupResponse;
import dz.kyrios.adminservice.entity.Group;

public interface GroupMapper {

    Group requestToEntity(GroupRequest request);

    GroupResponse entityToResponse(Group entity);
}
