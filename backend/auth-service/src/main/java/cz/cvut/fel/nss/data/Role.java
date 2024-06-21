package cz.cvut.fel.nss.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cz.cvut.fel.nss.data.Permission.ADMIN_READ;
import static cz.cvut.fel.nss.data.Permission.*;

/**
 * Enum representing various roles in the system.
 * Each role is associated with a set of permissions.
 */
@RequiredArgsConstructor
public enum Role {
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_CREATE,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    USER_READ,
                    USER_UPDATE
            )
    ),
    USER(
            Set.of(
                    USER_READ,
                    USER_UPDATE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    /**
     * Retrieves a list of authorities granted to the role.
     * Each authority is represented by a SimpleGrantedAuthority.
     *
     * @return a list of granted authorities
     */
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}