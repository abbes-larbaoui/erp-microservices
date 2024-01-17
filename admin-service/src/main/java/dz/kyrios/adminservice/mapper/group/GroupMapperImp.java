package dz.kyrios.adminservice.mapper.group;

import dz.kyrios.adminservice.dto.group.GroupRequest;
import dz.kyrios.adminservice.dto.group.GroupResponse;
import dz.kyrios.adminservice.entity.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupMapperImp implements GroupMapper{
    @Override
    public Group requestToEntity(GroupRequest request) {
        return Group.builder()
                .id(request.getId())
                .libelle(request.getLibelle())
                .actif(request.getActif())
                .build();
    }

    @Override
    public GroupResponse entityToResponse(Group entity) {
        return GroupResponse.builder()
                .id(entity.getId())
                .libelle(entity.getLibelle())
                .actif(entity.getActif())
                .build();
    }
}