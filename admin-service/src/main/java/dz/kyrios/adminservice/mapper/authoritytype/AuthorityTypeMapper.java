package dz.kyrios.adminservice.mapper.authoritytype;

import dz.kyrios.adminservice.dto.authoritytype.AuthorityTypeRequest;
import dz.kyrios.adminservice.dto.authoritytype.AuthorityTypeResponse;
import dz.kyrios.adminservice.entity.AuthorityType;

public interface AuthorityTypeMapper {

    AuthorityType requestToEntity(AuthorityTypeRequest request);

    AuthorityTypeResponse entityToResponse(AuthorityType entity);
}
