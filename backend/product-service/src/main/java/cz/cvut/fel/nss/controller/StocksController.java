package cz.cvut.fel.nss.controller;

import cz.cvut.fel.nss.data.Stock;
import cz.cvut.fel.nss.model.CreateStockRequest;
import cz.cvut.fel.nss.model.CreateStockResponse;
import cz.cvut.fel.nss.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
@AllArgsConstructor
public class StocksController {

    private StockService stockService;
//    @PostMapping
//    public ResponseEntity<CreateStockResponse> createStock(@RequestBody CreateStockRequest request) {
//        CreateStockResponse response = stockService.createStock(request);
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
    @PostMapping
    public ResponseEntity<Stock> createStock(
            @RequestBody CreateStockRequest request
    ) {
        Stock savedStock = stockService.createStock(request);
        return ResponseEntity.ok(savedStock);
    }
}
