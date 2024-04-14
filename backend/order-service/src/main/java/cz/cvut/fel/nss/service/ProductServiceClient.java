package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.model.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductServiceClient {
    @GetMapping("/product/{orderId}")
    List<ProductResponse> getProductsByOrderId(@PathVariable("orderId") Long orderId);
}
