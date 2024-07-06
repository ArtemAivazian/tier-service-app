package cz.cvut.fel.nss.service.impl;

import cz.cvut.fel.nss.data.Stock;
import cz.cvut.fel.nss.dto.StockLdo;
import cz.cvut.fel.nss.exception.StockNotFoundException;
import cz.cvut.fel.nss.mapper.Mapper;
import cz.cvut.fel.nss.repository.StockRepository;
import cz.cvut.fel.nss.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Implementation of the StockService interface.
 */
@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;

    /**
     * Creates a new stock.
     *
     * @param stockLdo the stock data
     * @return the created stock
     */
    @CacheEvict(value = "stocks", allEntries = true)
    public StockLdo createStock(StockLdo stockLdo) {
        Stock stock = Mapper.mapToStock(stockLdo);
        Stock savedStock = stockRepository.save(stock);
        return Mapper.mapToStockLdo(savedStock);
    }

    /**
     * Updates an existing stock.
     *
     * @param id       the ID of the stock to update
     * @param stockLdo the stock data
     * @return the updated stock
     */
    @CachePut(value = "stocks", key = "#id")
    public StockLdo updateStock(Long id, StockLdo stockLdo) {
        Stock stock = stockRepository.findById(id).orElseThrow(()
                -> new StockNotFoundException("Stock not found with id " + id));

        stock.setAddress(stockLdo.getAddress());

        Stock updatedStock = stockRepository.save(stock);
        return Mapper.mapToStockLdo(updatedStock);
    }

    /**
     * Deletes a stock by its ID.
     *
     * @param id the ID of the stock to delete
     */
    @CacheEvict(value = "stocks", key = "#id")
    public void deleteStock(Long id) {
        Stock stock = stockRepository.findById(id).orElseThrow(()
                -> new StockNotFoundException("Stock not found with id " + id));
        stockRepository.delete(stock);
    }

    /**
     * Finds a stock by its ID.
     *
     * @param id the ID of the stock
     * @return the found stock
     */
    @Cacheable(value = "stocks", key = "#id")
    public StockLdo findStockById(Long id) {
        Stock stock = stockRepository.findById(id).orElseThrow(()
                -> new StockNotFoundException("Stock not found with id " + id));
        return Mapper.mapToStockLdo(stock);
    }
}
