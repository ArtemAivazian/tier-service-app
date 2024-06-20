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

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductsController {
    private final ProductsService productsService;
    private final ModelMapper mapper;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> createProduct(
            @Valid @RequestBody ProductDto productRequest
    ) throws StockNotFoundException {
        ProductLdo productLdo = mapper.map(productRequest, ProductLdo.class);
        ProductLdo savedProduct = productsService.createProduct(productLdo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.map(savedProduct, ProductDto.class));
    }

    @GetMapping("/all")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductLdo> allProducts = productsService.findAllProducts();

        return ResponseEntity.ok(allProducts.stream()
                .map(productDto -> mapper.map(productDto, ProductDto.class))
                .toList());
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductDto> getProductByName(
            @PathVariable("name") String name
    ) {
        ProductLdo productLdo = productsService.findProductByName(name);

        return ResponseEntity.ok(mapper.map(productLdo, ProductDto.class));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDto productRequest
    ) throws StockNotFoundException {
        ProductLdo productLdo = mapper.map(productRequest, ProductLdo.class);
        ProductLdo updatedProduct = productsService.updateProduct(id, productLdo);

        return ResponseEntity.ok(mapper.map(updatedProduct, ProductDto.class));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productsService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}



