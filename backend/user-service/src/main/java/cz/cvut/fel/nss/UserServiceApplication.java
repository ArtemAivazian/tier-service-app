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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * UserServiceApplication is the entry point for the user service application.
 * It enables service discovery, Feign clients, and caching.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
public class UserServiceApplication {

    /**
     * The main method to run the UserServiceApplication.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    /**
     * Bean definition for BCryptPasswordEncoder.
     *
     * @return a new instance of BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean definition for ModelMapper with strict matching strategy.
     *
     * @return a new instance of ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    /**
     * Bean definition for Feign Logger Level.
     *
     * @return the full logger level for Feign
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * Bean definition for FeignErrorDecoder.
     *
     * @return a new instance of FeignErrorDecoder
     */
    @Bean
    public FeignErrorDecoder getFeignErrorDecoder() {
        return new FeignErrorDecoder();
    }

    /**
     * Bean definition for HttpExchangeRepository.
     *
     * @return a new instance of InMemoryHttpExchangeRepository
     */
    @Bean
    public HttpExchangeRepository httpTraceRepository(){
        return new InMemoryHttpExchangeRepository();
    }

}