package cz.cvut.fel.nss.service.impl;

import cz.cvut.fel.nss.data.Stock;
import cz.cvut.fel.nss.dto.StockDto;
import cz.cvut.fel.nss.model.CreateStockRequest;
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

    public StockDto createStock(StockDto stockDto) {
        Stock stock = mapper.map(stockDto, Stock.class);
        Stock savedStock = stockRepository.save(stock);
        StockDto response = mapper.map(savedStock, StockDto.class);
        return response;
    }
}
