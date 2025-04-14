package com.sungurovpavel.online_store.controller;

import com.sungurovpavel.online_store.dto.ProductDTO;
import com.sungurovpavel.online_store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public Page<ProductDTO> getAllWithFilterAndSortProducts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "reviews") String sortType,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new IllegalArgumentException("minPrice не может быть больше maxPrice");
        }
        validateSortParams(sortType, sortDirection);
        Pageable pageable = PageRequest.of(page, size);
        return productService.getProductsByFilterAndSort(categoryId, minPrice, maxPrice, searchTerm,
                sortType, sortDirection ,pageable);
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

    private void validateSortParams(String sortType, String sortDirection) {
        // Проверка, что sortDirection корректен (asc или desc)
        if (!sortDirection.equalsIgnoreCase("asc") && !sortDirection.equalsIgnoreCase("desc")) {
            throw new IllegalArgumentException("Сортировка должно быть 'asc' или 'desc'");
        }
        // Проверка, что sortType корректен
        Set<String> allowedSortTypes = Set.of("price", "newest", "rating", "reviews");
        if (!allowedSortTypes.contains(sortType.toLowerCase())) {
            throw new IllegalArgumentException("Недопустимый тип сортировки: " + sortType);
        }
    }
}
