package dz.kyrios.adminservice.dto.profile;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileRequest {
    private Long id;
    private Long userId;
    private Long groupId;
    private String libelle;
    private Boolean actif;
}
