package cz.cvut.fel.nss.service;

import cz.cvut.fel.nss.dto.ProductDto;
import cz.cvut.fel.nss.exception.StockNotFoundException;

import java.util.List;

public interface ProductsService {
    ProductDto createProduct(ProductDto productDto) throws StockNotFoundException;
    List<ProductDto> findAllProducts();
    ProductDto findProductByName(String name);
}
