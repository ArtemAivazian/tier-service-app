package cz.cvut.fel.nss.facade;

import cz.cvut.fel.nss.dto.ProductDto;
import cz.cvut.fel.nss.dto.ProductLdo;
import cz.cvut.fel.nss.exception.StockNotFoundException;
import cz.cvut.fel.nss.mapper.Mapper;
import cz.cvut.fel.nss.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Facade for managing products.
 */
@Component
@RequiredArgsConstructor
public class ProductFacade {
    private final ProductsService productsService;

    /**
     * Creates a new product.
     *
     * @param productRequest the product request object
     * @return the created product as a ProductDto
     * @throws StockNotFoundException if the stock is not found
     */
    public ProductDto createProduct(ProductDto productRequest) throws StockNotFoundException {
        ProductLdo productLdo = Mapper.mapToProductLdo(productRequest);
        ProductLdo savedProduct = productsService.createProduct(productLdo);
        return Mapper.mapToProductDto(savedProduct);
    }

    /**
     * Retrieves all products.
     *
     * @return a list of ProductDto objects
     */
    public List<ProductDto> getAllProducts() {
        List<ProductLdo> allProducts = productsService.findAllProducts();
        return allProducts.stream()
                .map(Mapper::mapToProductDto)
                .toList();
    }

    /**
     * Retrieves a product by its name.
     *
     * @param name the name of the product
     * @return the product as a ProductDto
     */
    public ProductDto getProductByName(String name) {
        ProductLdo productLdo = productsService.findProductByName(name);
        return Mapper.mapToProductDto(productLdo);
    }

    /**
     * Updates an existing product.
     *
     * @param id             the product ID
     * @param productRequest the product request object
     * @return the updated product as a ProductDto
     * @throws StockNotFoundException if the stock is not found
     */
    public ProductDto updateProduct(Long id, ProductDto productRequest) throws StockNotFoundException {
        ProductLdo productLdo = Mapper.mapToProductLdo(productRequest);
        ProductLdo updatedProduct = productsService.updateProduct(id, productLdo);
        return Mapper.mapToProductDto(updatedProduct);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the product ID
     */
    public void deleteProduct(Long id) {
        productsService.deleteProduct(id);
    }
}
