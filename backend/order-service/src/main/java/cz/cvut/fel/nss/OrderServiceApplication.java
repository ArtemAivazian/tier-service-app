package cz.cvut.fel.nss;

import cz.cvut.fel.nss.exception.FeignErrorDecoder;
import feign.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * Main class for the Order Service application.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    /**
     * Configures the Feign logger level to FULL.
     *
     * @return the Feign logger level
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * Configures the Feign error decoder bean.
     *
     * @return the FeignErrorDecoder bean
     */
    @Bean
    public FeignErrorDecoder getFeignErrorDecoder() {
        return new FeignErrorDecoder();
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