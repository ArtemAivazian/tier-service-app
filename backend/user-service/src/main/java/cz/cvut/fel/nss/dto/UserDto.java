package cz.cvut.fel.nss.dto;

import cz.cvut.fel.nss.data.Role;
import cz.cvut.fel.nss.shared.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private static final long serialVersionUID = -953297098295050686L;

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private Role role;
    private List<OrderResponse> orders;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }
}
