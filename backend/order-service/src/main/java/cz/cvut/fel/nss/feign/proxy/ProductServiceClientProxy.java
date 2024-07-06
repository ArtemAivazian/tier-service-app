package cz.cvut.fel.nss.feign.proxy;

import cz.cvut.fel.nss.feign.ProductServiceClient;
import cz.cvut.fel.nss.shared.ProductDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Proxy class for ProductServiceClient to handle resilience patterns.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ProductServiceClientProxy implements ProductServiceClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceClientProxy.class);
    private final ProductServiceClient productServiceClient;

    /**
     * Retrieves product details by product name with retry and circuit breaker mechanisms.
     *
     * @param name          the name of the product
     * @param authorization the authorization header
     * @return the product details as a ProductDto
     */
    @Override
    @Retry(name = "product-service")
    @CircuitBreaker(name = "product-service", fallbackMethod = "getProductFallback")
    public ProductDto getProductByName(String name, String authorization) {
        LOGGER.info("Calling getProductByName with name: {}", name);
        return productServiceClient.getProductByName(name, authorization);
    }

    /**
     * Fallback method for getProductByName in case of failure.
     *
     * @param name          the name of the product
     * @param authorization the authorization header
     * @param exception     the exception that caused the fallback
     * @return null as a fallback response
     */
    @Override
    public ProductDto getProductFallback(String name, String authorization, Throwable exception) {
        LOGGER.error("Fallback method getProductFallback called due to exception: {}", exception.getMessage(), exception);
        return null;
    }
}
