package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.dto.StockDto;

public interface StockService {
    StockDto createStock(StockDto stockDto);
}
