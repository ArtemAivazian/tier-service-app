package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.dto.StockLdo;

public interface StockService {
    StockLdo createStock(StockLdo stockLdo);
    StockLdo updateStock(Long id, StockLdo stockLdo);
    void deleteStock(Long id);
    StockLdo findStockById(Long id);
}
