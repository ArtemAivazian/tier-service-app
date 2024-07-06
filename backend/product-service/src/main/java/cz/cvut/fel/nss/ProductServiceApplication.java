package cz.cvut.fel.nss;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main class for the Product Service application.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@EnableTransactionManagement
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    /**
     * Configures the HttpExchangeRepository bean for HTTP trace repository.
     *
     * @return the HttpExchangeRepository bean
     */
    @Bean
    public HttpExchangeRepository httpTraceRepository(){
        return new InMemoryHttpExchangeRepository();
    }
}