package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.dto.StockLdo;

public interface StockService {
    StockLdo createStock(StockLdo stockLdo);
}
