package cz.cvut.fel.nss.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
<<<<<<< HEAD
public class OrderDto implements Serializable {
    private Long orderId;
    private Long userId;
    private transient List<OrderedProduct> orderedProducts;
=======
@NoArgsConstructor
public class OrderDto {
    private Long userId;
    private Long orderId;
    @NotNull(message = "OrderedProducts cannot be null")
    private List<OrderedProductDto> orderedProducts;
>>>>>>> main
}
