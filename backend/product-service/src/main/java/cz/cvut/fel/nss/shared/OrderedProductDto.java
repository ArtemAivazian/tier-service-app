package cz.cvut.fel.nss.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object for an Ordered Product.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedProductDto {
    private Long productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Long stockId;
}
