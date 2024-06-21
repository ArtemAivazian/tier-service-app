package cz.cvut.fel.nss.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entity representing a stock.
 */
@Entity
@Table(name = "stocks")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @SequenceGenerator(
            name = "stock_sequence",
            sequenceName = "stock_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "stock_sequence"
    )
    private Long stockId;
    @Column(nullable = false)
    private String address;

    @OneToMany(
            mappedBy = "stock",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Product> products;
}
