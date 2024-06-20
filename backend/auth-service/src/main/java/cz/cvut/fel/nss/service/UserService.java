package cz.cvut.fel.nss.service;


import cz.cvut.fel.nss.dto.AuthDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AuthDto getUserDetailsByEmail(String email);

}
