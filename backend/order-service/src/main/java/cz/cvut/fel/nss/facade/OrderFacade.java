package cz.cvut.fel.nss.facade;

import cz.cvut.fel.nss.dto.OrderDto;
import cz.cvut.fel.nss.dto.OrderLdo;
import cz.cvut.fel.nss.mapper.Mapper;
import cz.cvut.fel.nss.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Facade for managing orders.
 */
@Component
@RequiredArgsConstructor
public class OrderFacade {
    private final OrderService orderService;

    /**
     * Retrieves the orders for a specific user.
     *
     * @param userId the user ID
     * @return a list of OrderDto objects
     */
    public List<OrderDto> getUserOrders(String userId) {
        List<OrderLdo> userOrders = orderService.getUserOrders(userId);
        return userOrders.stream()
                .map(Mapper::mapToOrderDto)
                .toList();
    }

    /**
     * Places a new order.
     *
     * @param orderRequest  the order request object
     * @param authorization the authorization header
     * @return the placed order as an OrderDto
     */
    public OrderDto placeOrder(OrderDto orderRequest, String authorization) {
        OrderLdo orderLdo = Mapper.mapToOrderLdo(orderRequest);
        OrderLdo placedOrder = orderService.placeOrder(orderLdo, authorization);
        return Mapper.mapToOrderDto(placedOrder);
    }
}
