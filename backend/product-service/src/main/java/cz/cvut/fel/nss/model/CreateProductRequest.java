package cz.cvut.fel.nss.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Long stockId;

}
