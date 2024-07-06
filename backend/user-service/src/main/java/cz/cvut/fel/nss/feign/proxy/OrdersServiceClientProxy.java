package cz.cvut.fel.nss.feign.proxy;


import cz.cvut.fel.nss.feign.OrdersServiceClient;
import cz.cvut.fel.nss.shared.OrderDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Proxy class for OrdersServiceClient to provide resilience mechanisms like Circuit Breaker and Retry.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class OrdersServiceClientProxy implements OrdersServiceClient {
    private final OrdersServiceClient ordersServiceClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersServiceClientProxy.class);

    /**
     * Retrieves a list of orders for a given user.
     *
     * @param userId the ID of the user
     * @param authorization the authorization token
     * @return a list of OrderDto objects
     */
    @Override
    @Retry(name = "order-service")
    @CircuitBreaker(name = "order-service", fallbackMethod = "getOrdersFallback")
    public List<OrderDto> getOrders(String userId, String authorization) {
        LOGGER.info("Calling getOrders with userId: {}", userId);
        return ordersServiceClient.getOrders(userId, authorization);
    }

    /**
     * Fallback method for getOrders in case of failure.
     *
     * @param userId the ID of the user
     * @param authorization the authorization token
     * @param exception the exception that caused the fallback
     * @return an empty list of OrderDto objects
     */
    @Override
    public List<OrderDto> getOrdersFallback(String userId, String authorization, Throwable exception) {
        LOGGER.error("Fallback method getOrdersFallback called due to exception: {}", exception.getMessage(), exception);
        return new ArrayList<>();
    }
}

