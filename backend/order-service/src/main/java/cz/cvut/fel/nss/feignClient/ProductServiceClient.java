package cz.cvut.fel.nss.feignClient;


import cz.cvut.fel.nss.shared.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "product-service")
public interface ProductServiceClient {
    @GetMapping("/product/{name}")
    ProductResponse getProductByName(
            @PathVariable("name") String name,
            @RequestHeader("Authorization") String authorization
    );

}
