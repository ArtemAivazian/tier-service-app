package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.dto.OrderLdo;

import java.util.List;

/**
 * Service interface for managing orders.
 */
public interface OrderService {
    /**
     * Retrieves the orders for a specific user.
     *
     * @param userId the user ID
     * @return a list of OrderLdo objects representing the user's orders
     */
    List<OrderLdo> getUserOrders(String userId);

    /**
     * Places a new order.
     *
     * @param orderRequestDto the order request object
     * @param authorization   the authorization header
     * @return the placed order as an OrderLdo object
     */
    OrderLdo placeOrder(OrderLdo orderRequestDto, String authorization);
}
