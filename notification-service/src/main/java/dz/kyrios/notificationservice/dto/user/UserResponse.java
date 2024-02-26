package dz.kyrios.notificationservice.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String uuid;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
