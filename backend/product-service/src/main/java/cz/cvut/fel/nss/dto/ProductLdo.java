package cz.cvut.fel.nss.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductLdo implements Serializable {
    @Serial
    private static final long serialVersionUID = -4280373382223640333L;
    private Long productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Long stockId;
}