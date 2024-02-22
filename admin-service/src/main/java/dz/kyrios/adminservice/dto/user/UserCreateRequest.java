package dz.kyrios.adminservice.dto.user;

import dz.kyrios.adminservice.enums.KeycloakRequiredAction;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {
    private String userName;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String password;
    private Boolean temporary;
    private Boolean emailVerified;
    private Boolean actif;
    private List<KeycloakRequiredAction> requiredActions;
}
