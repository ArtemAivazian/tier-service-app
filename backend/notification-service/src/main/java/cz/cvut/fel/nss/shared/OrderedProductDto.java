package cz.cvut.fel.nss.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
<<<<<<< HEAD:backend/order-service/src/main/java/cz/cvut/fel/nss/shared/OrderedProduct.java
public class OrderedProduct {
=======
public class OrderedProductDto {
>>>>>>> main:backend/notification-service/src/main/java/cz/cvut/fel/nss/shared/OrderedProductDto.java
    private Long productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Long stockId;
}
