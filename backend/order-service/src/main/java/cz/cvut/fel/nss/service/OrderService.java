package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.dto.OrderDto;
import cz.cvut.fel.nss.model.OrderResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface OrderService {
    List<OrderDto> getUserOrders(String userId);
    OrderDto placeOrder(OrderDto orderRequestDto, String authorization);

}
