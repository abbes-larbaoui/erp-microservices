package dz.kyrios.adminservice.mapper.authoritytype;

import dz.kyrios.adminservice.dto.authoritytype.AuthorityTypeRequest;
import dz.kyrios.adminservice.dto.authoritytype.AuthorityTypeResponse;
import dz.kyrios.adminservice.entity.AuthorityType;
import org.springframework.stereotype.Component;

@Component
public class AuthorityTypeMapperImp implements AuthorityTypeMapper{
    @Override
    public AuthorityType requestToEntity(AuthorityTypeRequest request) {
        return AuthorityType.builder()
                .id(request.getId())
                .libelle(request.getLibelle())
                .actif(request.getActif())
                .build();
    }

    @Override
    public AuthorityTypeResponse entityToResponse(AuthorityType entity) {
        return AuthorityTypeResponse.builder()
                .id(entity.getId())
                .libelle(entity.getLibelle())
                .actif(entity.getActif())
                .build();
    }
}
