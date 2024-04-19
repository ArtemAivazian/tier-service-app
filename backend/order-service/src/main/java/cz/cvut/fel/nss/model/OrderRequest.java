package cz.cvut.fel.nss.model;

import cz.cvut.fel.nss.data.OrderedProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long userId;
    private List<OrderedProduct> orderedProducts;
}
