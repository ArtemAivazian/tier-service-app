package cz.cvut.fel.nss;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * checks, if jwt token is signed, before routing
 */
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {


    private final Environment env;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    @Getter
    public static class Config {
        private List<String> authorities;
        public void setAuthorities(String authorities) {
            this.authorities = Arrays.asList(authorities.split(" "));
        }
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("authorities");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            //extract jwt token
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "").trim();

            // authorities
            List<String> authorities = getAuthorities(jwt);

            /*if 'authorities' list contains authorities and role that is specified in application.yml
            in filter 'AuthorizationHeaderFilter=' */
            boolean hasRequiredAuthority = authorities.stream()
                    .anyMatch(authority->config.getAuthorities().contains(authority));

            //if it is not valid -> HttpStatus.UNAUTHORIZED
            if(!hasRequiredAuthority) {
                return onError(exchange,"User is not authorized to perform this operation", HttpStatus.FORBIDDEN);
            }
            //if it is valid, continue
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        DataBufferFactory bufferFactory = response.bufferFactory();
        DataBuffer dataBuffer = bufferFactory.wrap(err.getBytes());

        return response.writeWith(Mono.just(dataBuffer));
    }

    private List<String> getAuthorities(String jwt) {
        List<String> returnValue = new ArrayList<>();
        try {
            Claims claims = extractAllClaims(jwt);
            String authoritiesStr = claims.get("authorities", String.class); // Get authorities as a single String
            if (authoritiesStr != null) {
                // Split the string on commas to convert it into a List<String>
                List<String> authorities = Arrays.asList(authoritiesStr.split(","));
                returnValue.addAll(authorities);
            }
        } catch (Exception e) {
            return returnValue;
        }

        return returnValue;
    }


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

//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
    public List<String> extractAuthorities(String token) {
        List<String> authorities = extractAllClaims(token).get("authorities", List.class);
        return authorities;
    }
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
}
