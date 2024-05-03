package cz.cvut.fel.nss.service.impl;

import cz.cvut.fel.nss.data.Product;
import cz.cvut.fel.nss.data.Stock;
import cz.cvut.fel.nss.dto.ProductDto;
import cz.cvut.fel.nss.dto.StockDto;
import cz.cvut.fel.nss.exception.StockNotFoundException;
import cz.cvut.fel.nss.mapper.Mapper;
import cz.cvut.fel.nss.model.CreateProductRequest;
import cz.cvut.fel.nss.model.ProductResponse;
import cz.cvut.fel.nss.repository.ProductRepository;
import cz.cvut.fel.nss.repository.StockRepository;
import cz.cvut.fel.nss.service.ProductsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@AllArgsConstructor
public class ProductsServiceImpl implements ProductsService {
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    public ProductDto createProduct(ProductDto productDto) throws StockNotFoundException {
        Stock stock = stockRepository.findByStockId(productDto.getStockId());
        if (stock == null) throw new StockNotFoundException("Stock is not found by id " + productDto.getStockId() );

        Product product = Mapper.mapToProduct(productDto, stock);

        Product savedProduct = productRepository.save(product);

        ProductDto response = Mapper.mapToProductDto(savedProduct);
        return response;
    }

    public List<ProductDto> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(Mapper::mapToProductDto)
                .toList();
    }

    @Override
    @Cacheable(value = "productServiceCache", key = "#name")
    public ProductDto findProductByName(String name) {
        Product product = productRepository.findByName(name);
        ProductDto productDto = Mapper.mapToProductDto(product);
        return productDto;
    }


//    @Transactional
//    public Product patchProduct(Long productId, Map<String, Object> updates) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
//        Map<String, Object> changes = applyUpdatesAndTrackChanges(product, updates);
//        Product updatedProduct = productRepository.save(product);
//
////        if (!changes.isEmpty()) {
////            ProductUpdatedEvent event = new ProductUpdatedEvent(productId, changes);
////            productProducer.sendMessage(event);
////        }
//
//        return updatedProduct;
//    }
//    private Map<String, Object> applyUpdatesAndTrackChanges(Product product, Map<String, Object> updates) {
//        Map<String, Object> changes = new HashMap<>();
//
//        updates.forEach((key, value) -> {
//            switch (key) {
//                case "name":
//                    if (!product.getName().equals(value)) {
//                        product.setName((String) value);
//                        changes.put("name", value);
//                    }
//                    break;
//                case "price":
//                    BigDecimal newPrice = BigDecimal.valueOf((Double) value);
//                    if (!product.getPrice().equals(newPrice)) {
//                        product.setPrice(newPrice);
//                        changes.put("price", value);
//                    }
//                    break;
//                case "quantity":
//                    if (!product.getQuantity().equals(value)) {
//                        product.setQuantity((Integer) value);
//                        changes.put("quantity", value);
//                    }
//                    break;
//                case "stockId":
//                    if (!product.getStock().equals(value)) {
//                        product.setStock((Stock) value);
//                        changes.put("stockId", value);
//                    }
//                    break;
//            }
//        });
//
//        return changes;
//    }
}
