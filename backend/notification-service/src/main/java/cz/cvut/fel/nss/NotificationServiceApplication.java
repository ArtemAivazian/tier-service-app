package cz.cvut.fel.nss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main entry point for the Notification Service application.
 * This class is responsible for starting the Spring Boot application.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NotificationServiceApplication {
    /**
     * Main method which serves as the entry point for the Spring Boot application.
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}