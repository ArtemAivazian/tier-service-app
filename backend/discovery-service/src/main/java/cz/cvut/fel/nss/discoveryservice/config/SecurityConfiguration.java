package cz.cvut.fel.nss.discoveryservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for security settings of the Discovery Service.
 * This class configures HTTP security to require basic authentication for all requests.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity to modify
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while configuring security
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(httpBasic -> httpBasic
                        .realmName("DiscoveryRealm")
                )
                .build();
    }
}
