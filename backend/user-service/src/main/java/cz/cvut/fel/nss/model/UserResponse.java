package cz.cvut.fel.nss.model;

import cz.cvut.fel.nss.data.Role;
import cz.cvut.fel.nss.shared.OrderResponse;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private List<OrderResponse> orders;

}