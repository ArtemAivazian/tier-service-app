package cz.cvut.fel.nss.model;

import cz.cvut.fel.nss.data.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private State state;
    private List<ProductResponse> products;
}
