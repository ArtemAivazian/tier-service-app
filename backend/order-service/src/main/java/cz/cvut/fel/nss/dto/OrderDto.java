package cz.cvut.fel.nss.dto;

import cz.cvut.fel.nss.data.OrderedProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto implements Serializable {

    private Long orderId;
    private Long userId;
    private List<OrderedProduct> orderedProducts;
}
