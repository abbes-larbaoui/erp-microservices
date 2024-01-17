package dz.kyrios.adminservice.dto.authoritytype;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorityTypeRequest {
    private Long id;
    private String libelle;
    private Boolean actif;
}
