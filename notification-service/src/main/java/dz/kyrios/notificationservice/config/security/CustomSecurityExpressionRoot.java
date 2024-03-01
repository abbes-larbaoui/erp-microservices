package dz.kyrios.notificationservice.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
public class CustomSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private final String permissionServiceUri = "http://localhost:8080/admin-service/api/v1/permission";

    private final String authorityParamName = "authority";

    private final String moduleParamName = "module";

    private final String moduleName = "notification-service";

    private Object filterObject;
    private Object returnObject;
    private Object target;

    private final RestTemplate restTemplate;
    public CustomSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
        this.restTemplate = new RestTemplate();
    }

    public boolean hasCustomAuthority(String authority) {
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
            Jwt jwt = jwtAuthentication.getToken();

            String url = UriComponentsBuilder.fromUriString(permissionServiceUri)
                    .queryParam(authorityParamName, authority)
                    .queryParam(moduleParamName, moduleName)
                    .build()
                    .toUriString();

            // Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwt.getTokenValue());

            // Create entity
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            // Send the request
            try {
                ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.GET, entity, Boolean.class);
                return Boolean.TRUE.equals(response.getBody());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        return false;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    public void setThis(Object target) {
        this.target = target;
    }
}
