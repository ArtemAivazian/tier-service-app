package cz.cvut.fel.nss.feign;

import cz.cvut.fel.nss.shared.ProductDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Feign client for interacting with the product service.
 */
@FeignClient(name = "product-service")
public interface ProductServiceClient {

    /**
     * Retrieves product details by product name.
     *
     * @param name          the name of the product
     * @param authorization the authorization header
     * @return the product details as a ProductDto
     */
    @GetMapping("/product/{name}")
    @Retry(name = "product-service")
    @CircuitBreaker(name = "product-service", fallbackMethod = "getProductFallback")
    ProductDto getProductByName(
            @PathVariable("name") String name,
            @RequestHeader("Authorization") String authorization
    );

    /**
     * Fallback method for getProductByName in case of failure.
     *
     * @param name          the name of the product
     * @param authorization the authorization header
     * @param exception     the exception that caused the fallback
     * @return null as a fallback response
     */
    default ProductDto getProductFallback(String name, String authorization, Throwable exception) {
        return null;
    }
}
