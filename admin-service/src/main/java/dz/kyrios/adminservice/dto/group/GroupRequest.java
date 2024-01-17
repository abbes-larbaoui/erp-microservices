package dz.kyrios.adminservice.dto.group;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupRequest {
    private Long id;
    private String libelle;
    private Boolean actif;
}
