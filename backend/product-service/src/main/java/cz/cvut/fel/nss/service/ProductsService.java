package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.data.Product;
import cz.cvut.fel.nss.data.Stock;
import cz.cvut.fel.nss.model.CreateProductRequest;
import cz.cvut.fel.nss.model.ProductResponse;
import cz.cvut.fel.nss.model.ProductUpdatedEvent;
import cz.cvut.fel.nss.model.dto.ProductDto;
import cz.cvut.fel.nss.repository.ProductRepository;
import cz.cvut.fel.nss.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@AllArgsConstructor
public class ProductsService {
    private ProductRepository productRepository;
    private StockRepository stockRepository;
    private ProductProducer productProducer;
    @Transactional
    public Product createProduct(CreateProductRequest request) {
        Optional<Stock> stock = stockRepository.findById(request.getStockId());
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .stock(stock.get())
                .build();
        return productRepository.save(product);
    }

    public List<ProductResponse> findAllProducts() {
//        List<Product> products = productRepository.findAll();
//        List<ProductResponse> responses = new ArrayList<>();
//        for (var product : products) {
//            ProductResponse response = ProductResponse.builder()
//                    .productId(product.getProductId())
//                    .orderId(product.getOrderId())
//                    .name(product.getName())
//                    .price(product.getPrice())
//                    .quantity(product.getQuantity())
//                    .build();
//            responses.add(response);
//        }
//        return responses;
        return null;
    }

    public List<ProductResponse> getProductsByOrderId(Long orderId) {
//        List<Product> products = productRepository.findAllByOrderId(orderId);
//        List<ProductResponse> responses = new ArrayList<>();
//        for (var product : products) {
//            ProductResponse response = ProductResponse.builder()
//                    .productId(product.getProductId())
//                    .orderId(product.getOrderId())
//                    .name(product.getName())
//                    .price(product.getPrice())
//                    .quantity(product.getQuantity())
//                    .build();
//            responses.add(response);
//        }
//        return responses;
        return null;
    }


    @Transactional
    public Product patchProduct(Long productId, Map<String, Object> updates) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
        Map<String, Object> changes = applyUpdatesAndTrackChanges(product, updates);
        Product updatedProduct = productRepository.save(product);

        if (!changes.isEmpty()) {
            ProductUpdatedEvent event = new ProductUpdatedEvent(productId, changes);
            productProducer.sendMessage(event);
        }

        return updatedProduct;
    }
    private Map<String, Object> applyUpdatesAndTrackChanges(Product product, Map<String, Object> updates) {
        Map<String, Object> changes = new HashMap<>();

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    if (!product.getName().equals(value)) {
                        product.setName((String) value);
                        changes.put("name", value);
                    }
                    break;
                case "price":
                    BigDecimal newPrice = BigDecimal.valueOf((Double) value);
                    if (!product.getPrice().equals(newPrice)) {
                        product.setPrice(newPrice);
                        changes.put("price", value);
                    }
                    break;
                case "quantity":
                    if (!product.getQuantity().equals(value)) {
                        product.setQuantity((Integer) value);
                        changes.put("quantity", value);
                    }
                    break;
                case "stockId":
                    if (!product.getStock().equals(value)) {
                        product.setStock((Stock) value);
                        changes.put("stockId", value);
                    }
                    break;
//                case "orderId":
//                    if (product.getOrderId() == null) {
//                        product.setOrderId(Long.valueOf(String.valueOf(value)));
//                        changes.put("orderId", value);
//                    }
//                    if (!product.getOrderId().equals(value)) {
//                        product.setOrderId(Long.valueOf(String.valueOf(value)));
//                        changes.put("orderId", value);
//                    }
            }
        });

        return changes;
    }
}
