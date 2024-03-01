package dz.kyrios.adminservice.mapper.usersession;

import dz.kyrios.adminservice.dto.user.UserSessionResponse;
import dz.kyrios.adminservice.entity.User;
import dz.kyrios.adminservice.mapper.user.UserMapper;
import dz.kyrios.adminservice.service.UserService;
import org.keycloak.representations.idm.UserSessionRepresentation;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class UserSessionMapperImp implements UserSessionMapper {

    @Override
    public UserSessionResponse keycloakSessionToUserSessionResponse(UserSessionRepresentation userSessionRepresentation) {

        return UserSessionResponse.builder()
                .sessionId(userSessionRepresentation.getId())
                .start(LocalDateTime.ofInstant(Instant.ofEpochMilli(userSessionRepresentation.getStart()), ZoneId.systemDefault()))
                .lastAccess(LocalDateTime.ofInstant(Instant.ofEpochMilli(userSessionRepresentation.getLastAccess()), ZoneId.systemDefault()))
                .ipAddress(userSessionRepresentation.getIpAddress())
                .build();
    }
}
