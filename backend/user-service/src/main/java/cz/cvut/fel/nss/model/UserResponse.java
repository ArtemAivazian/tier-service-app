package cz.cvut.fel.nss.model;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderResponse> orders;

}