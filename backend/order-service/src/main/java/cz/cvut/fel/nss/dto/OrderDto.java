package cz.cvut.fel.nss.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for an Order.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long userId;
    private Long orderId;
    @NotNull(message = "OrderedProducts cannot be null")
    private List<OrderedProductDto> orderedProducts;
}
