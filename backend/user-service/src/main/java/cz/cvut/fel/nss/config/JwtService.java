package cz.cvut.fel.nss.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * JwtService provides methods to work with JWT tokens, including extracting claims and authorities.
 */
@Service
@AllArgsConstructor
public class JwtService {
    private Environment env;

    /**
     * Retrieves the signing key for JWT based on the secret specified in the environment properties.
     *
     * @return the signing key
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(env.getProperty("token.secret"));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts all claims from a JWT token.
     *
     * @param token the JWT token
     * @return the claims extracted from the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extracts the subject (typically the user identifier) from a JWT token.
     *
     * @param token the JWT token
     * @return the subject extracted from the token
     */
    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from a JWT token using a provided claims resolver function.
     *
     * @param token the JWT token
     * @param claimsResolver the function to resolve the claim
     * @param <T> the type of the claim
     * @return the claim extracted from the token
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Retrieves the authorities (roles) from a JWT token.
     *
     * @param jwt the JWT token
     * @return a collection of granted authorities or empty list if an error occurs
     */
    public Collection<? extends GrantedAuthority> getAuthorities(String jwt) {
        List<String> returnValue = new ArrayList<>();
        try {
            Claims claims = extractAllClaims(jwt);
            String authoritiesStr = claims.get("authorities", String.class); // Get authorities as a single String
            if (authoritiesStr != null) {
                List<String> authorities = Arrays.asList(authoritiesStr.split(","));
                returnValue.addAll(authorities);
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
        return returnValue.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
