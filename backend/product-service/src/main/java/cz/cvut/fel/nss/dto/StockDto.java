package cz.cvut.fel.nss.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {
    private Long stockId;
    @NotNull(message="Stock address cannot be null")
    private String address;
}
