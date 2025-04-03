package com.sungurovpavel.online_store.controller;

import com.sungurovpavel.online_store.dto.ProductDTO;
import com.sungurovpavel.online_store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public Page<ProductDTO> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50")  int size) {
                            Pageable pageable = PageRequest.of(page, size);
                            return productService.getAllProducts(pageable);
    }

    @GetMapping("/products/{id}")
    public ProductDTO getProduct(@PathVariable UUID id) {
        return productService.getProduct(id);
    }

    @PostMapping("/products")
    public ProductDTO addNewProduct(@RequestBody ProductDTO productDTO) {
        return productService.saveProduct(productDTO);
    }

    @PutMapping("/products/{id}")
    public ProductDTO updateProduct(@PathVariable UUID id, @RequestBody ProductDTO productDTO) {
        return productService.saveProduct(productDTO);
    }

    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return "Product with id " + id + " was deleted";
    }
}
