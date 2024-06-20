package cz.cvut.fel.nss.data;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long productId;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false)
    private BigDecimal price;
    @Column(nullable=false)
    private Integer quantity;
    @ManyToOne
    @JoinColumn(
            name = "stock_id",
            referencedColumnName = "stockId"
    )
    @NotNull(message = "Stock cannot be null")
    private Stock stock;

}
