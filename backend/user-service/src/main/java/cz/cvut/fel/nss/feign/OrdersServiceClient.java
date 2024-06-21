package cz.cvut.fel.nss.feign;

import cz.cvut.fel.nss.shared.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;


/**
 * OrdersServiceClient is a Feign client interface for communicating with the order service.
 */
@FeignClient(name = "order-service")
public interface OrdersServiceClient {

    /**
     * Retrieves the list of orders for a specific user.
     *
     * @param userId the ID of the user
     * @param authorization the authorization header
     * @return a list of OrderDto objects representing the user's orders
     */
    @GetMapping("/users/{userId}/orders")
    List<OrderDto> getOrders(
            @PathVariable("userId") String userId,
            @RequestHeader("Authorization") String authorization
    );

}
