package cz.cvut.fel.nss.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.nss.model.AuthenticationRequest;
import cz.cvut.fel.nss.model.UserDto;
import cz.cvut.fel.nss.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthenticationFilter(
            AuthService authService,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        super(authenticationManager);
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req,
            HttpServletResponse res)
            throws AuthenticationException {
        try {

            AuthenticationRequest creds = new ObjectMapper().readValue(req.getInputStream(), AuthenticationRequest.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication auth
    ) throws IOException, ServletException {

        String userName = ((User) auth.getPrincipal()).getUsername();
        UserDto userDetails = authService.getUserDetailsByEmail(userName);

        String token = jwtService.generateToken(userDetails);

        res.addHeader("token", token);
        res.addHeader("userId", userDetails.getUserId().toString());
    }
}
