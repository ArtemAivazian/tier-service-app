package cz.cvut.fel.nss.controller;

import cz.cvut.fel.nss.data.Product;
import cz.cvut.fel.nss.model.CreateProductRequest;
import cz.cvut.fel.nss.model.ProductResponse;
import cz.cvut.fel.nss.model.dto.ProductDto;
import cz.cvut.fel.nss.service.ProductsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class  ProductsController {

    private final ProductsService productsService;
    @PatchMapping("/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Product> patchProduct(
            @PathVariable("productId") Long productId,
            @RequestBody Map<String, Object> updates
    ) {
        Product patchedProduct = productsService.patchProduct(productId, updates);
        return ResponseEntity.status(HttpStatus.OK).body(patchedProduct);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(
            @RequestBody CreateProductRequest request
    ) {
        Product product = productsService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/all")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> responses = productsService.findAllProducts();
        return ResponseEntity.ok(responses);
    }


    @GetMapping("/{orderId}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<ProductResponse>> getProductsByOrderId(
            @PathVariable("orderId") Long orderId
    ) {
        List<ProductResponse> responses  = productsService.getProductsByOrderId(orderId);
        return ResponseEntity.ok(responses);
    }
}



