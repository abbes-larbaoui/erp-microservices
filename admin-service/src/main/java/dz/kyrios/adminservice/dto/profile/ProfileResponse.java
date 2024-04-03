package dz.kyrios.adminservice.dto.profile;

import dz.kyrios.adminservice.dto.authority.AuthorityResponse;
import dz.kyrios.adminservice.dto.group.GroupResponse;
import dz.kyrios.adminservice.dto.module.ModuleResponse;
import dz.kyrios.adminservice.dto.role.RoleResponse;
import dz.kyrios.adminservice.dto.user.UserResponse;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponse {
    private Long id;
    private UserResponse userResponse;
    private GroupResponse groupResponse;
    private String libelle;
    private Set<ModuleResponse> moduleResponses;
    private Set<RoleResponse> roleResponses;
    private Set<ProfileAuthorityResponse> authorityResponses;
    private Boolean actif;
}
