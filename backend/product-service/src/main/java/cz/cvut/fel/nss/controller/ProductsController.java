package cz.cvut.fel.nss.controller;

import cz.cvut.fel.nss.exception.StockNotFoundException;
import cz.cvut.fel.nss.dto.ProductDto;
import cz.cvut.fel.nss.dto.ProductLdo;
import cz.cvut.fel.nss.facade.ProductFacade;
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
    private final ProductFacade productFacade;

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
        ProductDto createdProduct = productFacade.createProduct(productRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdProduct);
    }

    /**
     * Retrieves all products.
     *
     * @return a list of ProductDto objects
     */
    @GetMapping("/all")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> allProducts = productFacade.getAllProducts();
        return ResponseEntity.ok(allProducts);
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
        ProductDto product = productFacade.getProductByName(name);
        return ResponseEntity.ok(product);
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
        ProductDto updatedProduct = productFacade.updateProduct(id, productRequest);
        return ResponseEntity.ok(updatedProduct);
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
        productFacade.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}



