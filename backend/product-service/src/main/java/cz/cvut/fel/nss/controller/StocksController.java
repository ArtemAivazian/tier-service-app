package cz.cvut.fel.nss.controller;

import cz.cvut.fel.nss.dto.StockDto;
import cz.cvut.fel.nss.model.CreateStockRequest;
import cz.cvut.fel.nss.model.StockResponse;
import cz.cvut.fel.nss.service.StockService;
import cz.cvut.fel.nss.service.impl.StockServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
@AllArgsConstructor
public class StocksController {
    private StockService stockService;
    private ModelMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StockResponse> createStock(
            @Valid @RequestBody CreateStockRequest request
    ) {
        StockDto stockDto = mapper.map(request, StockDto.class);
        StockDto savedStock = stockService.createStock(stockDto);
        StockResponse response = mapper.map(savedStock, StockResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
