package dz.kyrios.adminservice.mapper.usersession;

import dz.kyrios.adminservice.dto.user.UserSessionResponse;
import org.keycloak.representations.idm.UserSessionRepresentation;

public interface UserSessionMapper {
    UserSessionResponse keycloakSessionToUserSessionResponse(UserSessionRepresentation userSessionRepresentation);
}
