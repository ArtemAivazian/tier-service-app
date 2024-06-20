package cz.cvut.fel.nss.controller;

import cz.cvut.fel.nss.dto.StockLdo;
import cz.cvut.fel.nss.dto.StockDto;
import cz.cvut.fel.nss.service.StockService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
@AllArgsConstructor
public class StocksController {
    private StockService stockService;
    private ModelMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StockDto> createStock(
            @Valid @RequestBody StockDto request
    ) {
        StockLdo stockLdo = mapper.map(request, StockLdo.class);
        StockLdo savedStock = stockService.createStock(stockLdo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.map(savedStock, StockDto.class));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StockDto> updateStock(
            @PathVariable Long id,
            @Valid @RequestBody StockDto request
    ) {
        StockLdo stockLdo = mapper.map(request, StockLdo.class);
        StockLdo updatedStock = stockService.updateStock(id, stockLdo);

        return ResponseEntity.ok(mapper.map(updatedStock, StockDto.class));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDto> getStockById(@PathVariable Long id) {
        StockLdo stockLdo = stockService.findStockById(id);
        return ResponseEntity.ok(mapper.map(stockLdo, StockDto.class));
    }
}
