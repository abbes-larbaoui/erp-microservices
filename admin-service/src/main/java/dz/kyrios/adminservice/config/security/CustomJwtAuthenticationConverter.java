package dz.kyrios.adminservice.config.security;

import dz.kyrios.adminservice.entity.User;
import dz.kyrios.adminservice.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomJwtAuthenticationConverter extends JwtAuthenticationConverter {

    private final UserService userService;

    public CustomJwtAuthenticationConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        // Extract authorities from the JWT claims
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        Collection<GrantedAuthority> authoritiesFromJwt = jwtGrantedAuthoritiesConverter.convert(jwt);

        // Load additional authorities from the database
        String username = jwt.getClaimAsString("preferred_username");
        User user = userService.getOneByUsername(username);
        Set<GrantedAuthority> authoritiesFromDb = user.getActifProfile().getAuthorities()
                .stream()
                .filter(authority -> authority.getModule().getModuleCode().equals("admin-module"))
                .map(authority -> new SimpleGrantedAuthority(authority.getLibelle()))
                .collect(Collectors.toSet());

        // Combine authorities
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.addAll(authoritiesFromJwt);
        authorities.addAll(authoritiesFromDb);

        return authorities;
    }
}
