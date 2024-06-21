package cz.cvut.fel.nss.event;

import cz.cvut.fel.nss.dto.OrderedProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Event representing the placement of an order.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
    private Long orderId;
    private Long userId;
    private List<OrderedProductDto> orderedProducts;

}
