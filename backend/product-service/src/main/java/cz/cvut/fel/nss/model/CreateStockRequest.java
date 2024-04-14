package cz.cvut.fel.nss.model;

import cz.cvut.fel.nss.data.Product;
import cz.cvut.fel.nss.model.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateStockRequest {
    private String address;
}
