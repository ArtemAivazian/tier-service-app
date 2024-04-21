package cz.cvut.fel.nss.event;

import cz.cvut.fel.nss.shared.OrderedProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
    private Long orderId;
    private Long userId;
    private List<OrderedProduct> orderedProducts;

}
