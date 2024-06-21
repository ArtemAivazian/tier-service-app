package cz.cvut.fel.nss.config;


import cz.cvut.fel.nss.dto.AuthDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

/**
 * Service class for handling JWT operations such as token generation.
 */
@Service
@AllArgsConstructor
public class JwtService {
    private Environment env;

    /**
     * Generates a JWT token for the given user.
     *
     * @param user the user for whom the token is to be generated
     * @return the generated JWT token
     */
    public String generateToken(AuthDto user) {
        return Jwts.builder()
                .setSubject(user.getUserId().toString())
                .claim("authorities", populateAuthorities(user.getAuthorities()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.jwt_expiration_time"))))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Retrieves the signing key used for signing JWT tokens.
     *
     * @return the signing key
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(env.getProperty("token.secret"));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Converts a collection of GrantedAuthority objects to a comma-separated string.
     *
     * @param authorities the collection of GrantedAuthority objects
     * @return a comma-separated string of authorities
     */
    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = new HashSet<>();
        for(GrantedAuthority authority: authorities) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}
