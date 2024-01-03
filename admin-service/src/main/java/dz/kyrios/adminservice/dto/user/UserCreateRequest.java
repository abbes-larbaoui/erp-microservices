package dz.kyrios.adminservice.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Boolean temporary;
}
