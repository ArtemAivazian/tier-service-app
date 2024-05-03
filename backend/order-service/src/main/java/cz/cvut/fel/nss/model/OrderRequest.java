package cz.cvut.fel.nss.model;

import cz.cvut.fel.nss.data.OrderedProduct;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
//    @NotNull(message = "User id cannot be null")
    private Long userId;
    @NotNull(message = "OrderedProducts cannot be null")
    private List<OrderedProduct> orderedProducts;
}
