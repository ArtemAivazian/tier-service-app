package cz.cvut.fel.nss.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entity representing an ordered product.
 */
@Entity
@Table(name = "ordered_products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderedProduct implements Serializable {
    @Serial
    private static final long serialVersionUID = 3910154680585178877L;
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
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Long stockId;
}
