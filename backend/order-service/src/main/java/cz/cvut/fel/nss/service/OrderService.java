package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.data.Order;
import cz.cvut.fel.nss.model.OrderInitRequest;
import cz.cvut.fel.nss.model.OrderInitResponse;
import cz.cvut.fel.nss.model.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse getOrder(Long orderId);

    List<OrderResponse> getUserOrders(String userId, String authorization);

    OrderInitResponse initOrder(OrderInitRequest request);
}
