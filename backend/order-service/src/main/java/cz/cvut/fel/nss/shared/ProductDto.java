package cz.cvut.fel.nss.shared;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long productId;
    @NotNull(message="Product name cannot be null")
    private String name;
    @NotNull(message="Product price cannot be null")
    private BigDecimal price;
    @NotNull(message="Product quantity cannot be null")
    private Integer quantity;
    @NotNull(message="Product stock id cannot be null")
    private Long stockId;

}