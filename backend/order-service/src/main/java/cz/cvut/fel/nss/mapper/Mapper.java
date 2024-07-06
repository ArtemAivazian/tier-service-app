package cz.cvut.fel.nss.mapper;

import cz.cvut.fel.nss.data.Order;
import cz.cvut.fel.nss.data.OrderedProduct;
import cz.cvut.fel.nss.dto.OrderDto;
import cz.cvut.fel.nss.dto.OrderLdo;
import cz.cvut.fel.nss.dto.OrderedProductDto;
import cz.cvut.fel.nss.event.OrderPlacedEvent;

import java.util.stream.Collectors;

/**
 * Mapper class for converting between Order and OrderDto objects.
 */
public class Mapper {

    /**
     * Maps an OrderDto to an OrderLdo.
     *
     * @param orderDto the OrderDto object
     * @return the mapped OrderLdo object
     */
    public static OrderLdo mapToOrderLdo(OrderDto orderDto) {
        return OrderLdo.builder()
                .userId(orderDto.getUserId())
                .orderId(orderDto.getOrderId())
                .orderedProducts(orderDto.getOrderedProducts().stream()
                        .map(Mapper::mapToOrderedProduct)
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * Maps an OrderLdo to an OrderDto.
     *
     * @param orderLdo the OrderLdo object
     * @return the mapped OrderDto object
     */
    public static OrderDto mapToOrderDto(OrderLdo orderLdo) {
        return OrderDto.builder()
                .userId(orderLdo.getUserId())
                .orderId(orderLdo.getOrderId())
                .orderedProducts(orderLdo.getOrderedProducts().stream()
                        .map(Mapper::mapToOrderedProductDto)
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * Maps an OrderedProductDto to an OrderedProduct.
     *
     * @param orderedProductDto the OrderedProductDto object
     * @return the mapped OrderedProduct object
     */
    private static OrderedProduct mapToOrderedProduct(OrderedProductDto orderedProductDto) {
        return OrderedProduct.builder()
                .productId(orderedProductDto.getProductId())
                .name(orderedProductDto.getName())
                .price(orderedProductDto.getPrice())
                .quantity(orderedProductDto.getQuantity())
                .stockId(orderedProductDto.getStockId())
                .build();
    }

    /**
     * Maps an OrderedProduct to an OrderedProductDto.
     *
     * @param orderedProduct the OrderedProduct object
     * @return the mapped OrderedProductDto object
     */
    public static OrderedProductDto mapToOrderedProductDto(OrderedProduct orderedProduct) {
        return OrderedProductDto.builder()
                .productId(orderedProduct.getProductId())
                .name(orderedProduct.getName())
                .price(orderedProduct.getPrice())
                .quantity(orderedProduct.getQuantity())
                .stockId(orderedProduct.getStockId())
                .build();
    }

    /**
     * Maps an Order to an OrderLdo.
     *
     * @param order the Order entity
     * @return the mapped OrderLdo object
     */
    public static OrderLdo mapToOrderLdo(Order order) {
        return OrderLdo.builder()
                .userId(order.getUserId())
                .orderId(order.getOrderId())
                .orderedProducts(order.getOrderedProducts())
                .build();
    }

    /**
     * Maps an OrderLdo to an Order entity.
     *
     * @param orderLdo the OrderLdo object
     * @return the mapped Order entity
     */
    public static Order mapToOrder(OrderLdo orderLdo) {
        return Order.builder()
                .userId(orderLdo.getUserId())
                .orderId(orderLdo.getOrderId())
                .orderedProducts(orderLdo.getOrderedProducts())
                .build();
    }

    /**
     * Maps an Order to an OrderPlacedEvent.
     *
     * @param order the Order entity
     * @return the mapped OrderPlacedEvent object
     */
    public static OrderPlacedEvent mapToOrderPlacedEvent(Order order) {
        return OrderPlacedEvent.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .orderedProducts(order.getOrderedProducts().stream()
                        .map(Mapper::mapToOrderedProductDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
