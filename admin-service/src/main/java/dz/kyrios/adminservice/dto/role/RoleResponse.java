package dz.kyrios.adminservice.dto.role;

import dz.kyrios.adminservice.dto.authority.AuthorityResponse;
import dz.kyrios.adminservice.dto.module.ModuleResponse;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {
    private Long id;
    private String libelle;
    private Set<AuthorityResponse> authorityResponses;
    private ModuleResponse moduleResponse;
    private Boolean actif;
}
