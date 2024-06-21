package cz.cvut.fel.nss.mapper;

import cz.cvut.fel.nss.data.Product;
import cz.cvut.fel.nss.data.Stock;
import cz.cvut.fel.nss.dto.ProductLdo;

/**
 * Mapper class for converting between Product and ProductLdo objects.
 */
public class Mapper {

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
}
