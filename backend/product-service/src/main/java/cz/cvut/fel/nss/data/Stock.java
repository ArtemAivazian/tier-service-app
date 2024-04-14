package cz.cvut.fel.nss.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private String address;
}
