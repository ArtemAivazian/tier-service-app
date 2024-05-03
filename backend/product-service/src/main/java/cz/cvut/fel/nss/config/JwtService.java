package cz.cvut.fel.nss.config;

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


@Service
@AllArgsConstructor
public class JwtService {
    private Environment env;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(env.getProperty("token.secret"));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
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
            return null;
        }
        return returnValue.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }


}
