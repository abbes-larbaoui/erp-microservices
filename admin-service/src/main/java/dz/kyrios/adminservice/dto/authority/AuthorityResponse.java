package dz.kyrios.adminservice.dto.authority;

import dz.kyrios.adminservice.dto.authoritytype.AuthorityTypeResponse;
import dz.kyrios.adminservice.dto.module.ModuleResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorityResponse {
    private Long id;
    private String libelle;
    private AuthorityTypeResponse authorityTypeResponse;
    private ModuleResponse moduleResponse;
    private Boolean actif;
}
