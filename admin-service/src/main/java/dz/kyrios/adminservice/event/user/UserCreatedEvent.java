package dz.kyrios.adminservice.event.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreatedEvent {
    private String uuid;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
