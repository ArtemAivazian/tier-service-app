package cz.cvut.fel.nss.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInitResponse {
    private Long id;
    private Long userId;
    private String status;
}
