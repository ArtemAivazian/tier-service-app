package cz.cvut.fel.nss.controller;

import cz.cvut.fel.nss.exception.StockNotFoundException;
import cz.cvut.fel.nss.dto.ProductDto;
import cz.cvut.fel.nss.dto.ProductLdo;
import cz.cvut.fel.nss.service.ProductsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling product-related requests.
 */
@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductsController {
    private final ProductsService productsService;
    private final ModelMapper mapper;

    /**
     * Creates a new product.
     *
     * @param productRequest the product request object
     * @return the created product as a ProductDto
     * @throws StockNotFoundException if the stock is not found
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> createProduct(
            @Valid @RequestBody ProductDto productRequest
    ) throws StockNotFoundException
    {
        ProductLdo productLdo = mapper.map(productRequest, ProductLdo.class);
        ProductLdo savedProduct = productsService.createProduct(productLdo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.map(savedProduct, ProductDto.class));
    }

    /**
     * Retrieves all products.
     *
     * @return a list of ProductDto objects
     */
    @GetMapping("/all")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductLdo> allProducts = productsService.findAllProducts();

        return ResponseEntity.ok(allProducts.stream()
                .map(productDto -> mapper.map(productDto, ProductDto.class))
                .toList());
    }

    /**
     * Retrieves a product by its name.
     *
     * @param name the name of the product
     * @return the product as a ProductDto
     */
    @GetMapping("/{name}")
    public ResponseEntity<ProductDto> getProductByName(
            @PathVariable("name") String name
    ) {
        ProductLdo productLdo = productsService.findProductByName(name);

        return ResponseEntity.ok(mapper.map(productLdo, ProductDto.class));
    }

    /**
     * Updates a product.
     *
     * @param id             the product ID
     * @param productRequest the product request object
     * @return the updated product as a ProductDto
     * @throws StockNotFoundException if the stock is not found
     */
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDto productRequest
    ) throws StockNotFoundException
    {
        ProductLdo productLdo = mapper.map(productRequest, ProductLdo.class);
        ProductLdo updatedProduct = productsService.updateProduct(id, productLdo);

        return ResponseEntity.ok(mapper.map(updatedProduct, ProductDto.class));
    }

    /**
     * Deletes a product.
     *
     * @param id the product ID
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productsService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}



