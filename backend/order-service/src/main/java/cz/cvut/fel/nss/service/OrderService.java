package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.dto.OrderLdo;

import java.util.List;

public interface OrderService {
    List<OrderLdo> getUserOrders(String userId);
    OrderLdo placeOrder(OrderLdo orderRequestDto, String authorization);
}
