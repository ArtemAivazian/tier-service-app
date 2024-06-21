package cz.cvut.fel.nss.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

/**
 * AuthorizationFilter is a custom filter that extends BasicAuthenticationFilter to handle JWT-based authorization.
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final Environment environment;
    private final JwtService jwtService;

    /**
     * Constructs an AuthorizationFilter with the provided authentication manager, environment, and JWT service.
     *
     * @param authenticationManager the authentication manager
     * @param environment the environment for accessing properties
     * @param jwtService the service for handling JWT operations
     */
    public AuthorizationFilter(AuthenticationManager authenticationManager,
                               Environment environment, JwtService jwtService) {
        super(authenticationManager);
        this.environment = environment;
        this.jwtService = jwtService;
    }

    /**
     * Filters each HTTP request to check for a valid authorization header and set the security context if valid.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(environment.getProperty("authorization.token.header.name"));

        if (authorizationHeader == null
                || !authorizationHeader.startsWith(environment.getProperty("authorization.token.header.prefix"))) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    /**
     * Retrieves the authentication token from the request if the authorization header contains a valid JWT.
     *
     * @param req the HTTP request
     * @return the authentication token or null if the JWT is invalid
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
        String authorizationHeader = req.getHeader(environment.getProperty("authorization.token.header.name"));

        if (authorizationHeader == null) {
            return null;
        }

        String jwt = authorizationHeader.replace(environment.getProperty("authorization.token.header.prefix"), "");

        String userId = jwtService.extractSubject(jwt);
        var authorities = jwtService.getAuthorities(jwt);

        if (userId == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userId, null, authorities);

    }
}


