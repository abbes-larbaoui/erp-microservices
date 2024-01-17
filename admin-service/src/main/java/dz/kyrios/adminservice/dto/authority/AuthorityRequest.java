package dz.kyrios.adminservice.dto.authority;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorityRequest {
    private Long id;
    private String libelle;
    private Long authorityTypeId;
    private Long moduleId;
    private Boolean actif;
}
