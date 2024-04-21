package cz.cvut.fel.nss.mapper;

import cz.cvut.fel.nss.data.Product;
import cz.cvut.fel.nss.data.Stock;
import cz.cvut.fel.nss.dto.ProductDto;

public class Mapper {
    public static Product mapToProduct(ProductDto productDto, Stock stock) {
        return Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .stock(stock)
                .build();
    }

    public static ProductDto mapToProductDto(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .stockId(product.getStock().getStockId())
                .build();
    }
}
