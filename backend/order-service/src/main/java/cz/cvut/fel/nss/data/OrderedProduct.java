package cz.cvut.fel.nss.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "ordered_products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderedProduct {

    @Id
    @SequenceGenerator(
            name = "ordered_product_sequence",
            sequenceName = "ordered_product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ordered_product_sequence"
    )
    private Long productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Long stockId;
//    @ManyToOne(
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY
//    )
//    @JoinColumn(
//            name = "order_id",
//            referencedColumnName = "orderId",
//            insertable = false,
//            updatable = false
//    )
//    private Order order;
}
