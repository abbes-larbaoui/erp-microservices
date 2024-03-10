package dz.kyrios.adminservice.service;

import dz.kyrios.adminservice.entity.Authority;
import dz.kyrios.adminservice.entity.Module;
import dz.kyrios.adminservice.entity.Profile;
import dz.kyrios.adminservice.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Service
@Transactional
@Slf4j
public class PermissionService {

    private final UserService userService;

    private final AuthorityService authorityService;

    private final ModuleService moduleService;

    public PermissionService(UserService userService, AuthorityService authorityService, ModuleService moduleService) {
        this.userService = userService;
        this.authorityService = authorityService;
        this.moduleService = moduleService;
    }

    public Boolean getPermissionForUser(String authorityLibelle, String moduleCode) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
        Jwt jwt = jwtAuthentication.getToken();

        String username = (String) jwt.getClaims().get("preferred_username");

        /* get authority by libelle and module name */
        Authority authority = authorityService.getAuthorityByLibelleAndModule(authorityLibelle, moduleCode);

        Module module = moduleService.getOneBycode(moduleCode);

        /* get user actif profile authorities and check if conatins the authority requested */
        Profile profile = userService.getOneByUsername(username).getActifProfile();

        /* return false for revoked authority or true if granted */
        if (profile.getAuthorities().stream().anyMatch(profileAuthority -> profileAuthority.getAuthority().equals(authority))) {
            return profile.getAuthorities().stream()
                    .anyMatch(profileAuthority -> profileAuthority.getAuthority().equals(authority) && profileAuthority.getGranted());
        }

        if (profile != null) {
            boolean hasMatchingAuth = profile.getRoles().stream().flatMap(role -> role.getAuthorities().stream())
                    .anyMatch(auth -> auth.equals(authority));

            boolean hasModule = profile.getModules().contains(module);

            return hasMatchingAuth && hasModule;
        } else {
            return false;
        }
    }
}
