package cz.cvut.fel.nss.service.impl;

import cz.cvut.fel.nss.data.Stock;
import cz.cvut.fel.nss.dto.StockLdo;
import cz.cvut.fel.nss.exception.ProductNotFoundException;
import cz.cvut.fel.nss.exception.StockNotFoundException;
import cz.cvut.fel.nss.repository.StockRepository;
import cz.cvut.fel.nss.service.StockService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final ModelMapper mapper;

    public StockLdo createStock(StockLdo stockLdo) {
        Stock stock = mapper.map(stockLdo, Stock.class);
        Stock savedStock = stockRepository.save(stock);
        return mapper.map(savedStock, StockLdo.class);
    }

    public StockLdo updateStock(Long id, StockLdo stockLdo) {
        Stock stock = stockRepository.findById(id).orElseThrow(()
                -> new StockNotFoundException("Stock not found with id " + id));

        stock.setAddress(stockLdo.getAddress());

        Stock updatedStock = stockRepository.save(stock);
        return mapper.map(updatedStock, StockLdo.class);
    }

    public void deleteStock(Long id) {
        Stock stock = stockRepository.findById(id).orElseThrow(()
                -> new StockNotFoundException("Stock not found with id " + id));
        stockRepository.delete(stock);
    }

    public StockLdo findStockById(Long id) {
        Stock stock = stockRepository.findById(id).orElseThrow(()
                -> new StockNotFoundException("Stock not found with id " + id));
        return mapper.map(stock, StockLdo.class);
    }
}
