package cz.cvut.fel.nss.service.impl;

import cz.cvut.fel.nss.data.Order;
import cz.cvut.fel.nss.data.OrderedProduct;
import cz.cvut.fel.nss.dto.OrderDto;
import cz.cvut.fel.nss.exception.CannotConstructKafkaMessageException;
import cz.cvut.fel.nss.exception.InsufficientAmountOfProductException;
import cz.cvut.fel.nss.exception.ProductNotFoundException;
import cz.cvut.fel.nss.feignClient.ProductServiceClient;
import cz.cvut.fel.nss.event.OrderPlacedEvent;
import cz.cvut.fel.nss.repository.OrderRepository;
import cz.cvut.fel.nss.service.OrderService;
import cz.cvut.fel.nss.shared.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    private final ProductServiceClient productServiceClient;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final ModelMapper mapper;


    @Override
    public List<OrderDto> getUserOrders(String userId) {
        List<Order> orders = orderRepository.getOrdersByUserId(Long.valueOf(userId));

        List<OrderDto> orderDtos = orders.stream()
                .map(order -> mapper.map(order, OrderDto.class))
                .toList();

        orderDtos.forEach(
                orderDto -> orderDto.setOrderedProducts(
                        orderRepository.getOrderedProductsByOrderId(orderDto.getOrderId()))
        );
        return orderDtos;
    }

    @Override
    public OrderDto placeOrder(OrderDto orderRequestDto, String authorization) {

        //VALIDATE ORDERED PRODUCTS
        List<OrderedProduct> orderedProducts = orderRequestDto.getOrderedProducts();
        orderedProducts.forEach(orderedProduct -> {
            ProductResponse product = productServiceClient.getProductByName(orderedProduct.getName(), authorization);
//            if (product == null) {
////                //TO-DO: send OrderFailedEvent
////                OrderFailedEvent orderFailedEvent = new OrderFailedEvent();
////                orderFailedEvent.setOrderId(event.getOrderId());
////                orderFailedEvent.setUserId(event.getUserId());
////                kafkaTemplate.send("order-failed-topic", event.getOrderId().toString(), orderFailedEvent);
//                throw new ProductNotFoundException(
//                        "Product " + orderedProduct.getName() + " is not found.");
//            }
            if (product.getQuantity() < orderedProduct.getQuantity()) {
//                //TO-DO: send OrderFailedEvent
//                OrderFailedEvent orderFailedEvent = new OrderFailedEvent();
//                orderFailedEvent.setOrderId(event.getOrderId());
//                orderFailedEvent.setUserId(event.getUserId());
//                kafkaTemplate.send("order-failed-topic", event.getOrderId().toString(), orderFailedEvent);
                throw new InsufficientAmountOfProductException(
                        "Insufficient amount of product " + orderedProduct.getName());
            }
        });

        Order order = mapper.map(orderRequestDto, Order.class);
        Order placedOrder = orderRepository.save(order);

        OrderPlacedEvent orderPlacedEvent = mapper.map(placedOrder, OrderPlacedEvent.class);
        String orderId = orderPlacedEvent.getOrderId().toString();

        try {
            SendResult<String, OrderPlacedEvent> result =
                    kafkaTemplate.send("order-placed-topic-sync", orderId, orderPlacedEvent)
                            .get();
            LOGGER.info("Synchronous message has been sent to OrderPlacedTopicSync");
        } catch (ExecutionException | InterruptedException e) {
            throw new CannotConstructKafkaMessageException("Message cannot be constructed");
        }

        CompletableFuture<SendResult<String, OrderPlacedEvent>> future =
                kafkaTemplate.send("order-placed-topic-async", orderId, orderPlacedEvent);
        LOGGER.info("Asynchronous message has been sent to OrderPlacedTopicAsync");

        OrderDto returnValue = mapper.map(placedOrder, OrderDto.class);
        return returnValue;

    }

}
