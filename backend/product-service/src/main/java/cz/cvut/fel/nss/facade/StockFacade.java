package cz.cvut.fel.nss.facade;

import cz.cvut.fel.nss.dto.StockDto;
import cz.cvut.fel.nss.dto.StockLdo;
import cz.cvut.fel.nss.mapper.Mapper;
import cz.cvut.fel.nss.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Facade for managing stocks.
 */
@Component
@RequiredArgsConstructor
public class StockFacade {
    private final StockService stockService;

    /**
     * Creates a new stock.
     *
     * @param stockRequest the stock request object
     * @return the created stock as a StockDto
     */
    public StockDto createStock(StockDto stockRequest) {
        StockLdo stockLdo = Mapper.mapToStockLdo(stockRequest);
        StockLdo savedStock = stockService.createStock(stockLdo);
        return Mapper.mapToStockDto(savedStock);
    }

    /**
     * Updates an existing stock.
     *
     * @param id           the stock ID
     * @param stockRequest the stock request object
     * @return the updated stock as a StockDto
     */
    public StockDto updateStock(Long id, StockDto stockRequest) {
        StockLdo stockLdo = Mapper.mapToStockLdo(stockRequest);
        StockLdo updatedStock = stockService.updateStock(id, stockLdo);
        return Mapper.mapToStockDto(updatedStock);
    }

    /**
     * Deletes a stock by its ID.
     *
     * @param id the stock ID
     */
    public void deleteStock(Long id) {
        stockService.deleteStock(id);
    }

    /**
     * Retrieves a stock by its ID.
     *
     * @param id the stock ID
     * @return the stock as a StockDto
     */
    public StockDto getStockById(Long id) {
        StockLdo stockLdo = stockService.findStockById(id);
        return Mapper.mapToStockDto(stockLdo);
    }
}
