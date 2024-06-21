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

/**
 * Controller for handling stock-related requests.
 */
@RestController
@RequestMapping("/stock")
@AllArgsConstructor
public class StocksController {
    private StockService stockService;
    private ModelMapper mapper;

    /**
     * Creates a new stock.
     *
     * @param request the stock request object
     * @return the created stock as a StockDto
     */
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

    /**
     * Updates a stock.
     *
     * @param id      the stock ID
     * @param request the stock request object
     * @return the updated stock as a StockDto
     */
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

    /**
     * Deletes a stock.
     *
     * @param id the stock ID
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves a stock by its ID.
     *
     * @param id the stock ID
     * @return the stock as a StockDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<StockDto> getStockById(@PathVariable Long id) {
        StockLdo stockLdo = stockService.findStockById(id);
        return ResponseEntity.ok(mapper.map(stockLdo, StockDto.class));
    }
}
