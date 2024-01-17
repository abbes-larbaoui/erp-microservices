package dz.kyrios.adminservice.dto.role;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequest {
    private Long id;
    private String libelle;
    private Long moduleId;
    private Boolean actif;
}
