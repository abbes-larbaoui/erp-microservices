package dz.kyrios.adminservice.dto.user;

import dz.kyrios.adminservice.dto.profile.ProfileResponse;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long  id;
    private String uuid;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
//    private Set<ProfileResponse> profileResponses;
//    private ProfileResponse actifProfile;
}
