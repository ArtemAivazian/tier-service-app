package cz.cvut.fel.nss.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import cz.cvut.fel.nss.dto.AuthDto;
import cz.cvut.fel.nss.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Custom authentication filter for handling user authentication.
 * This class extends the UsernamePasswordAuthenticationFilter to customize the authentication process.
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthService authService;
    private final JwtService jwtService;

    /**
     * Constructor for the AuthenticationFilter.
     *
     * @param authService the service to handle authentication-related operations
     * @param authenticationManager the authentication manager
     * @param jwtService the service to handle JWT operations
     */
    public AuthenticationFilter(
            AuthService authService,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        super(authenticationManager);
        this.authService = authService;
        this.jwtService = jwtService;
    }

    /**
     * Attempts to authenticate the user based on the credentials provided in the request.
     *
     * @param req the HttpServletRequest containing the login credentials
     * @param res the HttpServletResponse
     * @return the Authentication object if authentication is successful
     * @throws AuthenticationException if an authentication error occurs
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req,
            HttpServletResponse res)
            throws AuthenticationException {
        try {

            AuthDto creds = new ObjectMapper().readValue(req.getInputStream(), AuthDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles successful authentication by generating a JWT token and adding it to the response headers.
     *
     * @param req the HttpServletRequest
     * @param res the HttpServletResponse
     * @param chain the FilterChain
     * @param auth the Authentication object containing user details
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication auth
    ) {

        String userName = ((User) auth.getPrincipal()).getUsername();
        AuthDto userDetails = authService.getUserDetailsByEmail(userName);

        String token = jwtService.generateToken(userDetails);

        res.addHeader("token", token);
        res.addHeader("userId", userDetails.getUserId().toString());
    }
}
