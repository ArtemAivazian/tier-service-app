package cz.cvut.fel.nss.service;


import cz.cvut.fel.nss.dto.AuthDto;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Service interface for handling authentication-related operations.
 * This interface extends UserDetailsService to provide user details for authentication.
 */
public interface AuthService extends UserDetailsService {
    /**
     * Retrieves the user details by email.
     *
     * @param email the email of the user whose details are to be retrieved
     * @return an AuthDto containing the user details
     */
    AuthDto getUserDetailsByEmail(String email);
}
