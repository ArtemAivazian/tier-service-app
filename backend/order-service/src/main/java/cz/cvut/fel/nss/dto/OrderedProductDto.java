package cz.cvut.fel.nss.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 * Data Transfer Object for an Ordered Product.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderedProductDto {
    private Long productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Long stockId;
}
