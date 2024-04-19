package cz.cvut.fel.nss.service.impl;

import cz.cvut.fel.nss.data.Order;
import cz.cvut.fel.nss.model.*;
import cz.cvut.fel.nss.repository.OrderRepository;
import cz.cvut.fel.nss.service.OrderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
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
    public List<OrderResponse> getUserOrders(String userId) {
        List<Order> orders = orderRepository.getOrdersByUserId(Long.valueOf(userId));
        ModelMapper mapper = new ModelMapper();
        List<OrderDto> orderDtos = orders.stream()
                .map(order -> mapper.map(order, OrderDto.class))
                .toList();

        orderDtos.forEach(
                orderDto -> orderDto.setOrderedProducts(
                        orderRepository.getOrderedProductsByOrderId(orderDto.getOrderId()))
        );


        List<OrderResponse> returnValue = orderDtos.stream()
                .map(orderDto -> mapper.map(orderDto, OrderResponse.class))
                .toList();
        return returnValue;
    }

    @Override
    public OrderDto placeOrder(OrderDto orderRequestDto) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Order order = modelMapper.map(orderRequestDto, Order.class);
        Order placedOrder = orderRepository.save(order);
        //send Notification

        OrderDto returnValue = modelMapper.map(placedOrder, OrderDto.class);
        return returnValue;

    }

}
