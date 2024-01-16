package dz.kyrios.adminservice.service;

import dz.kyrios.adminservice.dto.user.UserCreateRequest;
import dz.kyrios.adminservice.dto.user.UserRequest;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Transactional
@Service
public class KeycloakService {

    @Value("${app.realm}")
    private String realm;

    private Keycloak keycloak;

    public String createUser(UserCreateRequest request) {

        // TODO: change grantType
        keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8088/auth/")
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
        userRepresentation.setEmailVerified(request.getEmailVerified());
        userRepresentation.setEnabled(request.getEnabled());

        // set other required values
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(request.getPassword());
        credentialRepresentation.setTemporary(request.getTemporary());
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
        userRepresentation.getRequiredActions()
                .addAll(request.getRequiredActions().stream().map(Enum::toString).toList());

        Response response = keycloak.realm(realm).users().create(userRepresentation);

        if (response != null && response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            return org.keycloak.admin.client.CreatedResponseUtil.getCreatedId(response); // returns String (Id of created User)
        }
        return "";
    }

    public boolean deleteUser(String uuid) {
        keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8088/auth")
                .realm(realm)
                .clientId("admin-cli")
                .grantType("password")
                .username("abbes_kyrios")
                .password("abbes")
                .build();

        Response response = keycloak.realm(realm).users().delete(uuid);
        return response.getStatus() == Response.Status.OK.getStatusCode();
    }

    public void editUser(String uuid, UserRequest request) {
        keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8088/auth")
                .realm(realm)
                .clientId("admin-cli")
                .grantType("password")
                .username("abbes_kyrios")
                .password("abbes")
                .build();
        UserResource userResource = keycloak.realm(realm).users().get(uuid);
        UserRepresentation user = userResource.toRepresentation();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        userResource.update(user);
    }

    public void userRequiredAction(String uuid, String[] requiredActions) {
        keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8088/auth")
                .realm(realm)
                .clientId("admin-cli")
                .grantType("password")
                .username("abbes_kyrios")
                .password("abbes")
                .build();

        UserResource userResource = keycloak.realm(realm).users().get(uuid);
        UserRepresentation user = userResource.toRepresentation();
        user.getRequiredActions().addAll(List.of(requiredActions));
        userResource.update(user);
    }
}