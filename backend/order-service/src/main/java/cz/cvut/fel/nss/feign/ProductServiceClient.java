package cz.cvut.fel.nss.feign;

import cz.cvut.fel.nss.shared.ProductDto;
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
    ProductDto getProductByName(
            @PathVariable("name") String name,
            @RequestHeader("Authorization") String authorization
    );

}
