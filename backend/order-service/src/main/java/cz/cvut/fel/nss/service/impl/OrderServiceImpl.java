package cz.cvut.fel.nss.service.impl;

import cz.cvut.fel.nss.data.Order;
import cz.cvut.fel.nss.model.OrderInitRequest;
import cz.cvut.fel.nss.model.OrderInitResponse;
import cz.cvut.fel.nss.model.OrderResponse;
import cz.cvut.fel.nss.model.ProductResponse;
import cz.cvut.fel.nss.repository.OrderRepository;
import cz.cvut.fel.nss.service.OrderService;
import cz.cvut.fel.nss.service.ProductServiceClient;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private ProductServiceClient feignClient;
    @Override
    public OrderResponse getOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.getOrderById(orderId);
        if (optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            List<ProductResponse> products = feignClient.getProductsByOrderId(orderId);
            OrderResponse orderResponse = OrderResponse.builder()
                    .orderId(orderId)
                    .userId(order.getUserId())
                    .description("Order with products")
                    .products(products)
                    .build();
            return orderResponse;
        }
        return null;

    }

    @Override
    public OrderInitResponse initOrder(OrderInitRequest request) {
        Order order = Order.builder()
                .userId(request.getUserId())
                .status("INIT")
                .build();
        OrderInitResponse response = OrderInitResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .status("Order has been initialized")
                .build();
        orderRepository.save(order);
        return response;
    }
}
