package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.dto.ProductLdo;
import cz.cvut.fel.nss.exception.StockNotFoundException;

import java.util.List;

public interface ProductsService {
    ProductLdo createProduct(ProductLdo productLdo) throws StockNotFoundException;
    ProductLdo updateProduct(Long id, ProductLdo productLdo) throws StockNotFoundException;
    void deleteProduct(Long id);
    List<ProductLdo> findAllProducts();
    ProductLdo findProductByName(String name);
}
