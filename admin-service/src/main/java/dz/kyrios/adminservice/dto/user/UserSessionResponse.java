package dz.kyrios.adminservice.dto.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSessionResponse {
    private String sessionId;
    private UserResponse user;
    private LocalDateTime start;
    private LocalDateTime lastAccess;
    private String ipAddress;
}
