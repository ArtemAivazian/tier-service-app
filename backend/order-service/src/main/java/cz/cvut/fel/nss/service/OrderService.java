package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.model.OrderDto;
import cz.cvut.fel.nss.model.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse getOrder(Long orderId);

    List<OrderResponse> getUserOrders(String userId);
    OrderDto placeOrder(OrderDto orderRequestDto);

}
