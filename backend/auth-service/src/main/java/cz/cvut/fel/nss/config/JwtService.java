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


@Service
@AllArgsConstructor
public class JwtService {
    private Environment env;
    public String generateToken(AuthDto user) {
        return Jwts.builder()
                .setSubject(user.getUserId().toString())
                .claim("authorities", populateAuthorities(user.getAuthorities()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.jwt_expiration_time"))))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(env.getProperty("token.secret"));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = new HashSet<>();
        for(GrantedAuthority authority: authorities) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
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
