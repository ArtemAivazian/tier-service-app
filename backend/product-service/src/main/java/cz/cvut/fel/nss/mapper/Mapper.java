package cz.cvut.fel.nss.mapper;

import cz.cvut.fel.nss.data.Product;
import cz.cvut.fel.nss.data.Stock;
import cz.cvut.fel.nss.dto.ProductLdo;

public class Mapper {
    public static Product mapToProduct(ProductLdo productLdo, Stock stock) {
        return Product.builder()
                .name(productLdo.getName())
                .price(productLdo.getPrice())
                .quantity(productLdo.getQuantity())
                .stock(stock)
                .build();
    }

    public static ProductLdo mapToProductDto(Product product) {
        return ProductLdo.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .stockId(product.getStock().getStockId())
                .build();
    }
}
