package dz.kyrios.adminservice.mapper.authority;

import dz.kyrios.adminservice.dto.authority.AuthorityRequest;
import dz.kyrios.adminservice.dto.authority.AuthorityResponse;
import dz.kyrios.adminservice.entity.Authority;

public interface AuthorityMapper {

    Authority requestToEntity(AuthorityRequest request);

    AuthorityResponse entityToResponse(Authority entity);
}
