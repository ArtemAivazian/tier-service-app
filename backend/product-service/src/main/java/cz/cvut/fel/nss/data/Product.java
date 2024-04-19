package cz.cvut.fel.nss.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "stock_id",
            referencedColumnName = "stockId"
    )
    private Stock stock;

}
