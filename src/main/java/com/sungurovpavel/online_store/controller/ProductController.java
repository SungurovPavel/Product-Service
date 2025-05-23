package com.sungurovpavel.online_store.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.sungurovpavel.online_store.dto.ProductDTO;
import com.sungurovpavel.online_store.dto.ResponseProductDTO;
import com.sungurovpavel.online_store.exception.IdMismatchException;
import com.sungurovpavel.online_store.service.ProductService;
import com.sungurovpavel.online_store.validation.AllowedSortDirection;
import com.sungurovpavel.online_store.validation.AllowedSortType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Product API", description = "Управление товарами")
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Получить товары с фильтрацией и сортировкой",
            description = "Позволяет фильтровать товары по категориям, цене и поисковому запросу, а также сортировать.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный запрос"),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры")
            }
    )
    @GetMapping("/products")
    public ResponseProductDTO getAllWithFilterAndSortProducts(
            @Parameter(description = "Список категорий (опционально)") @RequestParam(required = false) List<String> categoryNames,
            @Parameter(description = "Минимальная цена (>= 0)") @RequestParam(required = false) @Min(0) Integer minPrice,
            @Parameter(description = "Максимальная цена (>= 0)") @RequestParam(required = false) @Min(0) Integer maxPrice,
            @Parameter(description = "Поисковый запрос (опционально)") @RequestParam(required = false) String searchTerm,
            @Parameter(description = "Номер страницы (по умолчанию 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы (по умолчанию 50)") @RequestParam(defaultValue = "50") int size,
            @Parameter(description = "Поле сортировки: reviews, price, rating, newest (по умолчанию reviews)")
            @AllowedSortType @RequestParam(defaultValue = "reviews") String sortType,
            @Parameter(description = "Направление сортировки: asc, desc (по умолчанию desc)")
            @AllowedSortDirection @RequestParam(defaultValue = "desc") String sortDirection) {

        return productService.getProductsByFilterAndSort(categoryNames, minPrice, maxPrice, searchTerm,
                sortType, sortDirection, page, size);
    }

    @Operation(summary = "Получить товар по ID")
    @GetMapping("/products/{id}")
    public ProductDTO getProduct(@Parameter(description = "UUID товара") @PathVariable UUID id) {
        return productService.getProduct(id);
    }

    @Operation(summary = "Добавить новый товар")
    @ApiResponse(responseCode = "201", description = "Товар успешно создан")
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO addNewProduct(@RequestBody ProductDTO productDTO) {
        return productService.saveProduct(productDTO);
    }

    @Operation(
            summary = "Обновить товар",
            description = "Обновляет данные товара по его ID. ID в пути и в теле запроса должны совпадать.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Товар успешно обновлен"),
                    @ApiResponse(responseCode = "400", description = "Невалидные данные или несовпадение ID"),
                    @ApiResponse(responseCode = "404", description = "Товар не найден")
            }
    )
    @PutMapping("/products/{id}")
    public ProductDTO updateProduct(@Parameter(
                                            description = "UUID товара для обновления",
                                            required = true,
                                            example = "d3eebc99-9c0b-4ef8-bb6d-6bb9bd380a14"
                                    )
                                    @PathVariable UUID id,
                                    @RequestBody ProductDTO productDTO) {

        if (!id.equals(productDTO.getId())) {
            throw new IdMismatchException("ID в пути и ID товара не совпадают");
        }
        return productService.saveProduct(id, productDTO);
    }

    @Operation(
            summary = "Удалить товар",
            description = "Удаляет товар по его ID. После удаления возвращает статус 204 (No Content).",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Товар успешно удален")
            }
    )
    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(
            @Parameter(
                    description = "UUID товара для удаления",
                    required = true,
                    example = "d3eebc99-9c0b-4ef8-bb6d-6bb9bd380a14"
            )
            @PathVariable UUID id) {

        productService.deleteProduct(id);
    }

    @Operation(
            summary = "Частичное обновление товара (JSON Patch)",
            description = "Обновляет товар с помощью JSON Patch (RFC 6902)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Товар успешно обновлен"),
                    @ApiResponse(responseCode = "400", description = "Невалидный JSON Patch"),
                    @ApiResponse(responseCode = "404", description = "Товар не найден")
            }
    )
    @PatchMapping(
            path = "/products/{id}",
            consumes = "application/json-patch+json"
    )
    public ProductDTO partialUpdateProduct(
            @Parameter(description = "UUID товара", required = true)
            @PathVariable UUID id,
            @RequestBody JsonNode patchNode) {

        return productService.applyPatchToProduct(id, patchNode);
    }
}
