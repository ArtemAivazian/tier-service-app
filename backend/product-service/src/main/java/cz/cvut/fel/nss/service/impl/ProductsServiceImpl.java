package cz.cvut.fel.nss.service.impl;

import cz.cvut.fel.nss.data.Product;
import cz.cvut.fel.nss.data.Stock;
import cz.cvut.fel.nss.dto.ProductLdo;
import cz.cvut.fel.nss.exception.ProductNotFoundException;
import cz.cvut.fel.nss.exception.StockNotFoundException;
import cz.cvut.fel.nss.mapper.Mapper;
import cz.cvut.fel.nss.repository.ProductRepository;
import cz.cvut.fel.nss.repository.StockRepository;
import cz.cvut.fel.nss.service.ProductsService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the ProductsService interface.
 */
@Service
@AllArgsConstructor
public class ProductsServiceImpl implements ProductsService {
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    /**
     * Creates a new product.
     *
     * @param productLdo the product data
     * @return the created product
     * @throws StockNotFoundException if the stock is not found
     */
    @CacheEvict(value = "products", key = "#productLdo.name")
    public ProductLdo createProduct(ProductLdo productLdo)
            throws StockNotFoundException
    {
        Stock stock = stockRepository.findByStockId(productLdo.getStockId());
        if (stock == null)
            throw new StockNotFoundException("Stock is not found by id " + productLdo.getStockId() );

        Product existingProduct = productRepository.findByName(productLdo.getName());
        //merging products, if exist
        if (existingProduct != null) {
            existingProduct.setQuantity(existingProduct.getQuantity() + productLdo.getQuantity());
            Product updatedProduct = productRepository.save(existingProduct);
            return Mapper.mapToProductDto(updatedProduct);
        } else {
            Product product = Mapper.mapToProduct(productLdo, stock);
            Product savedProduct = productRepository.save(product);
            return Mapper.mapToProductDto(savedProduct);
        }
    }

    /**
     * Retrieves all products.
     *
     * @return a list of all products
     */
    public List<ProductLdo> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(Mapper::mapToProductDto)
                .toList();
    }

    /**
     * Finds a product by its name.
     *
     * @param name the name of the product
     * @return the found product
     */
    @Override
    @Cacheable(value = "products", key = "#name")
    public ProductLdo findProductByName(String name) {
        Product product = productRepository.findByName(name);
        return Mapper.mapToProductDto(product);
    }

    /**
     * Updates an existing product.
     *
     * @param id         the ID of the product to update
     * @param productLdo the product data
     * @return the updated product
     * @throws StockNotFoundException if the stock is not found
     */
    @CachePut(value = "products", key = "#id")
    public ProductLdo updateProduct(Long id, ProductLdo productLdo)
            throws StockNotFoundException
    {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new ProductNotFoundException("Product not found with id " + id));
        Stock stock = stockRepository.findByStockId(productLdo.getStockId());
        if (stock == null)
            throw new StockNotFoundException("Stock is not found by id " + productLdo.getStockId() );

        product.setName(productLdo.getName());
        product.setPrice(productLdo.getPrice());
        product.setQuantity(productLdo.getQuantity());
        product.setStock(stock);

        Product updatedProduct = productRepository.save(product);
        return Mapper.mapToProductDto(updatedProduct);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     */
    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresentOrElse(
                product -> productRepository.deleteById(id),
                () -> { throw new ProductNotFoundException("Product not found with id " + id); }
        );
    }

}
