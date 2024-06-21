package cz.cvut.fel.nss.discoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Main entry point for the Discovery Service application.
 * This class starts the Spring Boot application and enables the Eureka server.
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {

	/**
	 * Main method which serves as the entry point for the Spring Boot application.
	 *
	 * @param args Command line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServiceApplication.class, args);
	}

}
