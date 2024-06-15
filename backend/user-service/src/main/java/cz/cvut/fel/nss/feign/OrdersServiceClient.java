package cz.cvut.fel.nss.feign;

import cz.cvut.fel.nss.shared.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrdersServiceClient {
    @GetMapping("/users/{userId}/orders")
    List<OrderDto> getOrders(
            @PathVariable("userId") String userId,
            @RequestHeader("Authorization") String authorization
    );

}
