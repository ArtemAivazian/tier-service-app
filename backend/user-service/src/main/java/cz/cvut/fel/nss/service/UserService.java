package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto register(UserDto userDetails);
    UserDto getUserDetailsByEmail(String email);
    UserDto getUserByUserId(String userId, String authorization);
}
