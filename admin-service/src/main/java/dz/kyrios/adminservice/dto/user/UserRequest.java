package dz.kyrios.adminservice.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private Long  id;
    private String uuid;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
}
