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
    public ResponseEntity<StockDto> createStock(
            @Valid @RequestBody StockDto request
    ) {
        StockLdo stockLdo = mapper.map(request, StockLdo.class);
        StockLdo savedStock = stockService.createStock(stockLdo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.map(savedStock, StockDto.class));
    }
}
