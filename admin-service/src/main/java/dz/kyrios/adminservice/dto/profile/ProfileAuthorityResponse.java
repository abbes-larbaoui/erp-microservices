package dz.kyrios.adminservice.dto.profile;

import dz.kyrios.adminservice.dto.authority.AuthorityResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileAuthorityResponse {
    private Long id;
    private AuthorityResponse authority;
    private Boolean granted;
}
