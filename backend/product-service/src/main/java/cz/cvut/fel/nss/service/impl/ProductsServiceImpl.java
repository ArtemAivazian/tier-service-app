package cz.cvut.fel.nss.service.impl;

import cz.cvut.fel.nss.data.Product;
import cz.cvut.fel.nss.data.Stock;
import cz.cvut.fel.nss.dto.ProductLdo;
import cz.cvut.fel.nss.exception.StockNotFoundException;
import cz.cvut.fel.nss.mapper.Mapper;
import cz.cvut.fel.nss.repository.ProductRepository;
import cz.cvut.fel.nss.repository.StockRepository;
import cz.cvut.fel.nss.service.ProductsService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ProductsServiceImpl implements ProductsService {
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    public ProductLdo createProduct(ProductLdo productLdo) throws StockNotFoundException {
        Stock stock = stockRepository.findByStockId(productLdo.getStockId());
        if (stock == null) throw new StockNotFoundException("Stock is not found by id " + productLdo.getStockId() );

        Product product = Mapper.mapToProduct(productLdo, stock);

        Product savedProduct = productRepository.save(product);

        return Mapper.mapToProductDto(savedProduct);
    }

    public List<ProductLdo> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(Mapper::mapToProductDto)
                .toList();
    }

    @Override
    @Cacheable(value = "productServiceCache", key = "#name")
    public ProductLdo findProductByName(String name) {
        Product product = productRepository.findByName(name);
        return Mapper.mapToProductDto(product);
    }
}
