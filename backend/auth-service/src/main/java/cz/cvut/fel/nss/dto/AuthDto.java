package cz.cvut.fel.nss.dto;


import cz.cvut.fel.nss.data.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

/**
 * Data Transfer Object (DTO) for authentication purposes.
 * This class contains user details necessary for authentication and authorization.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -953297098295050686L;

    private Long userId;
    private String firstName;
    private String lastName;
    @NotNull(message="Email cannot be null")
    @Email
    private String email;
    @NotNull(message="Password cannot be null")
    @Size(min=8, max=16, message="Password must be equal or grater than 8 characters and less than 16 characters")
    private String password;
    private String encryptedPassword;
    private Role role;
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }
}
