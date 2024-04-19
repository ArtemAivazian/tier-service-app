package cz.cvut.fel.nss.service.impl;

import cz.cvut.fel.nss.data.Order;
import cz.cvut.fel.nss.model.*;
import cz.cvut.fel.nss.repository.OrderRepository;
import cz.cvut.fel.nss.service.OrderService;
import cz.cvut.fel.nss.service.ProductServiceClient;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private ProductServiceClient productServiceClient;
    @Override
    public OrderResponse getOrder(Long orderId) {
//        Order optionalOrder = orderRepository.getOrderByOrderId(orderId);
//        if (optionalOrder.isPresent()){
//            Order order = optionalOrder.get();
//            List<ProductResponse> products = feignClient.getProductsByOrderId(orderId);
//            OrderResponse orderResponse = OrderResponse.builder()
//                    .orderId(orderId)
//                    .userId(order.getUserId())
//                    .description("Order with products")
//                    .products(products)
//                    .build();
//            return orderResponse;
//        }
        return null;

    }

    @Override
    public List<OrderResponse> getUserOrders(String userId, String authorization) {
        List<Order> orders = orderRepository.getOrdersByUserId(Long.valueOf(userId));
        ModelMapper mapper = new ModelMapper();
        List<OrderDto> orderDtos = orders.stream()
                .map(order -> mapper.map(order, OrderDto.class))
                .toList();

        //TO DO : set products in orderDto using Feign
        orderDtos.forEach(
                orderDto -> orderDto.setProducts(
                        productServiceClient.getProductsByOrderId(orderDto.getOrderId(), authorization))
        );

        List<OrderResponse> returnValue = orderDtos.stream()
                .map(orderDto -> mapper.map(orderDto, OrderResponse.class))
                .toList();
        return returnValue;
    }

    @Override
    public OrderInitResponse initOrder(OrderInitRequest request) {
        Order order = Order.builder()
                .userId(request.getUserId())
                .status("INIT")
                .build();
        OrderInitResponse response = OrderInitResponse.builder()
                .id(order.getOrderId())
                .userId(order.getUserId())
                .status("Order has been initialized")
                .build();
        orderRepository.save(order);
        return response;
    }
}
