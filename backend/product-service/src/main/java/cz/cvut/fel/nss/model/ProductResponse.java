package cz.cvut.fel.nss.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductResponse {
    private Long productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
