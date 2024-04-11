package cz.cvut.fel.nss.model;

import cz.cvut.fel.nss.data.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String userId;

}
