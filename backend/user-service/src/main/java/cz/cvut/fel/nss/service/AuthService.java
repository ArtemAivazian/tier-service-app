package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.model.*;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    UserDto register(UserDto userDetails);
//    AuthenticationResponse authenticate(AuthenticationRequest request);
    UserDto getUserDetailsByEmail(String email);

    UserDto getUserByUserId(String userId, String authorization);

//    List<UserDto> getAllUsers();
//    void deleteByUserId(String userId);
}
