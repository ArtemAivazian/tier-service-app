package cz.cvut.fel.nss.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockLdo implements Serializable {
    @Serial
    private static final long serialVersionUID = 6111655498429054445L;
    private Long stockId;
    private String address;
}
