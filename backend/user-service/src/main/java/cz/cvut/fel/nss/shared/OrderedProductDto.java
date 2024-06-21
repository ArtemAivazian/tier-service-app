package cz.cvut.fel.nss.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * OrderedProductDto is a Data Transfer Object representing a product in an order.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedProductDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 7839527304507946156L;

    private Long productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Long stockId;
}
