package cz.cvut.fel.nss.model;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {
    private BigDecimal price;
    private Integer quantity;
    private Long stockId;
}
