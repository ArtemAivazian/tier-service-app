package cz.cvut.fel.nss.dto;

import cz.cvut.fel.nss.data.Role;
import cz.cvut.fel.nss.shared.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * UserLdo is a Lightweight Data Object representing a user, used for more efficient data transfer inside the microservice
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLdo implements Serializable {
    @Serial
    private static final long serialVersionUID = -953297098295050686L;

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private Role role;
    private List<OrderDto> orders;
}
