package cz.cvut.fel.nss.service.impl;

import cz.cvut.fel.nss.data.Order;
import cz.cvut.fel.nss.data.OrderedProduct;
import cz.cvut.fel.nss.dto.OrderLdo;
import cz.cvut.fel.nss.dto.OrderedProductDto;
import cz.cvut.fel.nss.exception.CannotConstructKafkaMessageException;
import cz.cvut.fel.nss.exception.InsufficientAmountOfProductException;
import cz.cvut.fel.nss.feign.ProductServiceClient;
import cz.cvut.fel.nss.event.OrderPlacedEvent;
import cz.cvut.fel.nss.repository.OrderRepository;
import cz.cvut.fel.nss.service.OrderService;
import cz.cvut.fel.nss.shared.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
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
    @Cacheable(value = "orderServiceCache", key = "#userId")
    public List<OrderLdo> getUserOrders(String userId) {
        List<Order> orders = orderRepository.getOrdersByUserId(Long.valueOf(userId));

        List<OrderLdo> orderLdos = orders.stream()
                .map(order -> mapper.map(order, OrderLdo.class))
                .toList();

        orderLdos.forEach(
                orderLdo -> orderLdo.setOrderedProducts(
                        orderRepository.getOrderedProductsByOrderId(orderLdo.getOrderId()))
        );
        return orderLdos;
    }



    @CacheEvict(value = "orderServiceCache", allEntries = true)
    public void clearAllCache() {
        // TODO document why this method is empty
    }

    @Override
    public OrderLdo placeOrder(OrderLdo orderRequest, String authorization) {

        //VALIDATE ORDERED PRODUCTS
        List<OrderedProduct> orderedProducts = orderRequest.getOrderedProducts();
        orderedProducts.forEach(orderedProduct -> {
            ProductResponse product = productServiceClient.getProductByName(orderedProduct.getName(), authorization);
            if (product.getQuantity() < orderedProduct.getQuantity()) {
                throw new InsufficientAmountOfProductException(
                        "Insufficient amount of product " + orderedProduct.getName());
            }
        });

        Order order = mapper.map(orderRequest, Order.class);
        Order placedOrder = orderRepository.save(order);

        List<OrderedProductDto> orderedProductsDtos = orderedProducts.stream()
                .map(orderedProduct -> mapper.map(orderedProduct, OrderedProductDto.class))
                .toList();


        OrderPlacedEvent orderPlacedEvent = mapper.map(placedOrder, OrderPlacedEvent.class);
        orderPlacedEvent.setOrderedProducts(orderedProductsDtos);
        String orderId = orderPlacedEvent.getOrderId().toString();

        try {
            kafkaTemplate.send("order-placed-topic-sync", orderId, orderPlacedEvent)
                            .get();
            LOGGER.info("Synchronous message has been sent to OrderPlacedTopicSync");
        } catch (ExecutionException | InterruptedException e) {
            throw new CannotConstructKafkaMessageException("Message cannot be constructed");
        }

        kafkaTemplate.send("order-placed-topic-async", orderId, orderPlacedEvent);
        LOGGER.info("Asynchronous message has been sent to OrderPlacedTopicAsync");



        return mapper.map(placedOrder, OrderLdo.class);

    }

}
