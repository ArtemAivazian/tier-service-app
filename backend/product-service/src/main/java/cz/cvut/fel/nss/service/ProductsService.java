package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.dto.ProductLdo;
import cz.cvut.fel.nss.exception.StockNotFoundException;

import java.util.List;

/**
 * Service interface for managing products.
 */
public interface ProductsService {

    /**
     * Creates a new product.
     *
     * @param productLdo the product data
     * @return the created product
     * @throws StockNotFoundException if the stock is not found
     */
    ProductLdo createProduct(ProductLdo productLdo) throws StockNotFoundException;

    /**
     * Updates an existing product.
     *
     * @param id         the ID of the product to update
     * @param productLdo the product data
     * @return the updated product
     * @throws StockNotFoundException if the stock is not found
     */
    ProductLdo updateProduct(Long id, ProductLdo productLdo) throws StockNotFoundException;

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     */
    void deleteProduct(Long id);

    /**
     * Retrieves all products.
     *
     * @return a list of all products
     */
    List<ProductLdo> findAllProducts();

    /**
     * Finds a product by its name.
     *
     * @param name the name of the product
     * @return the found product
     */
    ProductLdo findProductByName(String name);
}
