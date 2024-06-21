package cz.cvut.fel.nss;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

/**
 * Utility class for generating and handling JWT tokens.
 */
public class JwtUtil {
    private static final String SECRET_KEY = "9a2f8c4e6b0d71f3e8b925a45747f894a3d6bc70fa8d5e21a15a6d8c3b9a0e7c";

    /**
     * Generates a JWT token for a given user ID and role.
     *
     * @param userId the user ID
     * @param role   the user's role
     * @return the generated JWT token
     */
    public String generateToken(String userId, String role) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("authorities", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Retrieves the signing key for JWT tokens.
     *
     * @return the signing key
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}