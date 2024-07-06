package cz.cvut.fel.nss.mapper;

import cz.cvut.fel.nss.data.Product;
import cz.cvut.fel.nss.data.Stock;
import cz.cvut.fel.nss.dto.ProductDto;
import cz.cvut.fel.nss.dto.ProductLdo;
import cz.cvut.fel.nss.dto.StockDto;
import cz.cvut.fel.nss.dto.StockLdo;

/**
 * Mapper class for converting between DTOs and entities.
 */
public class Mapper {

    /**
     * Maps a ProductDto to a ProductLdo.
     *
     * @param productDto the ProductDto object
     * @return the mapped ProductLdo object
     */
    public static ProductLdo mapToProductLdo(ProductDto productDto) {
        return ProductLdo.builder()
                .productId(productDto.getProductId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .stockId(productDto.getStockId())
                .build();
    }

    /**
     * Maps a ProductLdo to a Product entity.
     *
     * @param productLdo the ProductLdo object
     * @param stock      the Stock entity associated with the product
     * @return the mapped Product entity
     */
    public static Product mapToProduct(ProductLdo productLdo, Stock stock) {
        return Product.builder()
                .name(productLdo.getName())
                .price(productLdo.getPrice())
                .quantity(productLdo.getQuantity())
                .stock(stock)
                .build();
    }

    /**
     * Maps a Product entity to a ProductLdo.
     *
     * @param product the Product entity
     * @return the mapped ProductLdo object
     */
    public static ProductLdo mapToProductDto(Product product) {
        return ProductLdo.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .stockId(product.getStock().getStockId())
                .build();
    }

    /**
     * Maps a ProductLdo to a ProductDto.
     *
     * @param productLdo the ProductLdo object
     * @return the mapped ProductDto object
     */
    public static ProductDto mapToProductDto(ProductLdo productLdo) {
        return ProductDto.builder()
                .productId(productLdo.getProductId())
                .name(productLdo.getName())
                .price(productLdo.getPrice())
                .quantity(productLdo.getQuantity())
                .stockId(productLdo.getStockId())
                .build();
    }

    /**
     * Maps a StockDto to a StockLdo.
     *
     * @param stockDto the StockDto object
     * @return the mapped StockLdo object
     */
    public static StockLdo mapToStockLdo(StockDto stockDto) {
        return StockLdo.builder()
                .stockId(stockDto.getStockId())
                .address(stockDto.getAddress())
                .build();
    }

    /**
     * Maps a StockLdo to a StockDto.
     *
     * @param stockLdo the StockLdo object
     * @return the mapped StockDto object
     */
    public static StockDto mapToStockDto(StockLdo stockLdo) {
        return StockDto.builder()
                .stockId(stockLdo.getStockId())
                .address(stockLdo.getAddress())
                .build();
    }

    /**
     * Maps a StockLdo to a Stock entity.
     *
     * @param stockLdo the StockLdo object
     * @return the mapped Stock entity
     */
    public static Stock mapToStock(StockLdo stockLdo) {
        return Stock.builder()
                .stockId(stockLdo.getStockId())
                .address(stockLdo.getAddress())
                .build();
    }

    /**
     * Maps a Stock entity to a StockLdo.
     *
     * @param stock the Stock entity
     * @return the mapped StockLdo object
     */
    public static StockLdo mapToStockLdo(Stock stock) {
        return StockLdo.builder()
                .stockId(stock.getStockId())
                .address(stock.getAddress())
                .build();
    }
}
