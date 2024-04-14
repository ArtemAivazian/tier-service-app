package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.data.Product;
import cz.cvut.fel.nss.data.Stock;
import cz.cvut.fel.nss.model.CreateStockRequest;
import cz.cvut.fel.nss.model.CreateStockResponse;
import cz.cvut.fel.nss.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StockService {
    private StockRepository stockRepository;

//    public CreateStockResponse createStock(CreateStockRequest request) {
//        Stock stock = new Stock();
//        stock.setAddress(request.getAddress());
//        stock.setProducts(request.getProducts().stream()
//                .map(p -> new Product(p.getName(), p.getPrice(), p.getQuantity()))
//                .collect(Collectors.toList()));
//
//        Stock savedStock = stockRepository.save(stock);
//
//        return new CreateStockResponse(savedStock.getId(), "Stock created successfully with products");
//    }
    public Stock createStock(CreateStockRequest request) {
        Stock stock =
                Stock.builder()
                        .address(request.getAddress())
                        .build();
        return stockRepository.save(stock);
    }
}
