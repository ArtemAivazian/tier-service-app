package cz.cvut.fel.nss.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )
    private Long orderId;
<<<<<<< HEAD
=======

>>>>>>> main
    private Long userId;
    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "order_id",
            referencedColumnName = "orderId",
            nullable = false
    )
    @NotNull(message = "OrderProducts cannot be null")
    private List<OrderedProduct> orderedProducts;
}
