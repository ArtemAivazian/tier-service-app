package cz.cvut.fel.nss.controller;

import cz.cvut.fel.nss.data.Product;
import cz.cvut.fel.nss.exception.StockNotFoundException;
import cz.cvut.fel.nss.model.CreateProductRequest;
import cz.cvut.fel.nss.model.ProductResponse;
import cz.cvut.fel.nss.dto.ProductDto;
import cz.cvut.fel.nss.service.ProductsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductsController {
    private final ProductsService productsService;
    private final ModelMapper mapper;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody CreateProductRequest request
    ) throws StockNotFoundException {
        ProductDto productDto = mapper.map(request, ProductDto.class);
        ProductDto savedProduct = productsService.createProduct(productDto);
        ProductResponse response = mapper.map(savedProduct, ProductResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductDto> productDtos = productsService.findAllProducts();
        List<ProductResponse> responses = productDtos.stream()
                .map(productDto -> mapper.map(productDto, ProductResponse.class))
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ProductResponse> getProductByName(
            @PathVariable("name") String name
    ) {
        ProductDto productDto = productsService.findProductByName(name);
        ProductResponse response = mapper.map(productDto, ProductResponse.class);
        return ResponseEntity.ok(response);
    }

}



