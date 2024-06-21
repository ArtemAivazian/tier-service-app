package cz.cvut.fel.nss.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cz.cvut.fel.nss.data.Permission.*;

/**
 * Role enum defines the roles in the application and their corresponding permissions.
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
     * Retrieves the authorities granted to the role.
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