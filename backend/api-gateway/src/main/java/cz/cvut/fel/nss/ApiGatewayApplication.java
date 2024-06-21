package cz.cvut.fel.nss;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main class for the API Gateway application.
 *
 * <p>This class serves as the entry point for the Spring Boot application. It enables
 * service discovery with the {@link EnableDiscoveryClient} annotation.</p>
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    /**
     * Main method to run the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}