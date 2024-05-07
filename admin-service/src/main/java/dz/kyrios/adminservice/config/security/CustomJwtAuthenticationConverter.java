package dz.kyrios.adminservice.config.security;

import dz.kyrios.adminservice.entity.User;
import dz.kyrios.adminservice.service.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomJwtAuthenticationConverter {

    private final UserService userService;

    public CustomJwtAuthenticationConverter(UserService userService) {
        this.userService = userService;
    }

    protected Set<GrantedAuthority> extractAuthorities(Jwt jwt) {
        /* Extract authorities from the JWT claims */
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        Collection<GrantedAuthority> authoritiesFromJwt = jwtGrantedAuthoritiesConverter.convert(jwt);

        /* Load authorities from the profile */
        String username = jwt.getClaimAsString("preferred_username");
        User user = userService.getOneByUsername(username);
        Set<GrantedAuthority> authoritiesFromDb = new HashSet<>();
        if (user.getActifProfile() != null && user.getActifProfile().getAuthorities() != null) {
            authoritiesFromDb = user.getActifProfile().getAuthorities()
                    .stream()
                    .filter(authority -> authority.getAuthority().getModule() != null
                            && authority.getAuthority().getModule().getModuleCode().equals("admin-module")
                            && authority.getGranted())
                    .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getLibelle()))
                    .collect(Collectors.toSet());
        }

        /* Load authorities from the profile roles */
        Set<GrantedAuthority> roleAuthorities = new HashSet<>();
        if (user.getActifProfile() != null && user.getActifProfile().getRoles() != null) {
            user.getActifProfile().getRoles()
                    .stream()
                    .filter(role -> role.getModule() != null && role.getModule().getModuleCode().equals("admin-module"))
                    .forEach(role -> {
                        if (role.getAuthorities() != null) {
                            role.getAuthorities().forEach(authority -> roleAuthorities.add(new SimpleGrantedAuthority(authority.getLibelle())));
                        }
                    });
        }

        /* Combine authorities */
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.addAll(authoritiesFromJwt);
        authorities.addAll(authoritiesFromDb);
        authorities.addAll(roleAuthorities);

        return authorities;
    }
}
