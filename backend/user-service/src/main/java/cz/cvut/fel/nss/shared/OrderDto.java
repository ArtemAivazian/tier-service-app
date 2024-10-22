package cz.cvut.fel.nss.shared;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * OrderDto is a Data Transfer Object representing an order, including validation constraints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1943330493881408313L;

    private Long userId;
    private Long orderId;
    @NotNull(message = "OrderedProducts cannot be null")
    private List<OrderedProductDto> orderedProducts;
}
