package dz.kyrios.adminservice.service;

import dz.kyrios.adminservice.dto.user.UserCreateRequest;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.Collections;

@Transactional
@Service
public class KeycloakService {

    @Value("${app.realm}")
    private String realm;

    private Keycloak keycloak;

    public String createUser(UserCreateRequest request) {

        keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8088/auth")
                .realm(realm)
                .clientId("admin-cli")
                .grantType("password")
                .username("abbes_kyrios")
                .password("abbes")
                .build();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(request.getUserName());
        userRepresentation.setFirstName(request.getFirstName());
        userRepresentation.setLastName(request.getLastName());
        userRepresentation.setEmail(request.getEmail());
// set other required values
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(request.getPassword());
        credentialRepresentation.setTemporary(request.getTemporary());
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        Response response = keycloak.realm(realm).users().create(userRepresentation);

        if (response != null && response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            return org.keycloak.admin.client.CreatedResponseUtil.getCreatedId(response); // returns String (Id of created User)
        }
        return "";
    }
}
