package com.sungurovpavel.online_store.controller;

import com.sungurovpavel.online_store.dto.ProductDTO;
import com.sungurovpavel.online_store.dto.ResponseProductDTO;
import com.sungurovpavel.online_store.service.ProductService;
import com.sungurovpavel.online_store.validation.AllowedSortDirection;
import com.sungurovpavel.online_store.validation.AllowedSortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseProductDTO getAllWithFilterAndSortProducts(
            @RequestParam(required = false) List<String> categoryNames,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @AllowedSortType @RequestParam(defaultValue = "reviews") String sortType,
            @AllowedSortDirection @RequestParam(defaultValue = "desc") String sortDirection) {

        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new IllegalArgumentException("minPrice не может быть больше maxPrice");
        }
        if (minPrice != null && minPrice < 0) {
            throw new IllegalArgumentException("minPrice не может быть отрицательным");
        }
        if (maxPrice != null && maxPrice < 0) {
            throw new IllegalArgumentException("maxPrice не может быть отрицательным");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> productPage = productService.getProductsByFilterAndSort(categoryNames, minPrice, maxPrice, searchTerm,
                sortType, sortDirection ,pageable);

        return ResponseProductDTO.builder()
                .products(productPage.getContent())
                .totalPages(productPage.getTotalPages())
                .currentPage(productPage.getNumber())
                .totalElements(productPage.getTotalElements())
                .pageSize(productPage.getSize())
                .build();
    }

    @GetMapping("/products/{id}")
    public ProductDTO getProduct(@PathVariable UUID id) {
        return productService.getProduct(id);
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO addNewProduct(@RequestBody ProductDTO productDTO) {
        return productService.saveProduct(productDTO);
    }

    @PutMapping("/products/{id}")
    public ProductDTO updateProduct(@PathVariable UUID id, @RequestBody ProductDTO productDTO) {
        if (!id.equals(productDTO.getId())) {
            throw new IllegalArgumentException("ID в пути и ID товара не совпадают");
        }
        return productService.saveProduct(productDTO);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }
}
