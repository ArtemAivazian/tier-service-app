package cz.cvut.fel.nss.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Long stockId;
}
