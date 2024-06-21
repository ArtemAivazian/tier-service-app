package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.dto.StockLdo;

/**
 * Service interface for managing stocks.
 */
public interface StockService {

    /**
     * Creates a new stock.
     *
     * @param stockLdo the stock data
     * @return the created stock
     */
    StockLdo createStock(StockLdo stockLdo);

    /**
     * Updates an existing stock.
     *
     * @param id       the ID of the stock to update
     * @param stockLdo the stock data
     * @return the updated stock
     */
    StockLdo updateStock(Long id, StockLdo stockLdo);

    /**
     * Deletes a stock by its ID.
     *
     * @param id the ID of the stock to delete
     */
    void deleteStock(Long id);

    /**
     * Finds a stock by its ID.
     *
     * @param id the ID of the stock
     * @return the found stock
     */
    StockLdo findStockById(Long id);
}
