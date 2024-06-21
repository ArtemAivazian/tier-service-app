package cz.cvut.fel.nss.dto;

import cz.cvut.fel.nss.data.OrderedProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Lightweight Data Object for an Order.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLdo implements Serializable {
    @Serial
    private static final long serialVersionUID = -6458302846804207796L;
    private Long userId;
    private Long orderId;
    private List<OrderedProduct> orderedProducts;
}
