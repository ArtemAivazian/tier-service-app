package cz.cvut.fel.nss.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateStockResponse {
    private Long stockId;
    private String message;

}

