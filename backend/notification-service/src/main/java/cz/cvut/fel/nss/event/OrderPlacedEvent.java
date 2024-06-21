package cz.cvut.fel.nss.event;

import cz.cvut.fel.nss.shared.OrderedProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Event representing an order being placed.
 * This event contains details about the order, including the order ID, user ID, and a list of ordered products.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
    private Long orderId;
    private Long userId;
    private List<OrderedProductDto> orderedProducts;

}
