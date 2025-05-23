package com.sungurovpavel.online_store.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sungurovpavel.online_store.dto.ProductDTO;
import com.sungurovpavel.online_store.dto.ResponseProductDTO;
import com.sungurovpavel.online_store.dto.mapper.ProductMapper;
import com.sungurovpavel.online_store.entity.Product;
import com.sungurovpavel.online_store.exception.InvalidPriceRangeException;
import com.sungurovpavel.online_store.exception.ProductNotFoundException;
import com.sungurovpavel.online_store.exception.ProductUpdateException;
import com.sungurovpavel.online_store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final DefaultSslBundleRegistry defaultSslBundleRegistry;

    @Override
    public ResponseProductDTO getProductsByFilterAndSort(List<String> categoryNames, Integer minPrice, Integer maxPrice,
                                                         String searchTerm, String sortType, String sortDirection,
                                                         Integer page, Integer size) {

        log.info("Начало фильтрации товаров и сортировки");
        log.debug("Параметры: categoryNames={}, minPrice={}, maxPrice={}, searchTerm='{}', sortType='{}', sortDirection='{}', " +
                        "page={}, size={}",
                categoryNames, minPrice, maxPrice, searchTerm, sortType, sortDirection, page, size);
        try {
            if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
                throw new InvalidPriceRangeException("minPrice не может быть больше maxPrice");
            }

            if (categoryNames != null) {
                categoryNames = categoryNames.stream()
                        .map(String::toLowerCase)
                        .collect(Collectors.toList());
            }

            Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), selectorSortField(sortType));

            Page<Product> products = productRepository.findByFiltersWithSorts(
                    categoryNames, minPrice, maxPrice, searchTerm, PageRequest.of(page, size, sort));
            Page<ProductDTO> productDTOs = products.map(product -> productMapper.productToDto(product));
            ResponseProductDTO response = ResponseProductDTO.builder()
                    .products(productDTOs.getContent())
                    .totalPages(products.getTotalPages())
                    .currentPage(products.getNumber() + 1)
                    .totalElements(products.getTotalElements())
                    .pageSize(products.getSize())
                    .build();
            log.debug("Результаты фильтрации: найдено {} товаров, всего страниц {}",
                    products.getNumberOfElements(), products.getTotalPages());
            log.info("Фильтрация товаров и сортировка завершена успешно");
            return response;
        } catch (Exception e) {
            log.error("Ошибка при фильтрации и сортировки товаров: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        log.info("Сохранение товара: {}", productDTO.getName());
        try {
            Product product = productMapper.toEntity(productDTO);
            Product savedProduct = productRepository.save(product);
            log.debug("Товар сохранён: ID={}, название={}", savedProduct.getId(), savedProduct.getName());
            log.info("Товар успешно сохранён");
            return productMapper.productToDto(savedProduct);
        } catch (Exception e) {
            log.error("Ошибка при сохранении товара: {}", productDTO.getName(), e);
            throw e;
        }
    }

    @Override
    public ProductDTO saveProduct(UUID id, ProductDTO productDTO) {

        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Товар не найден с id: " + id);
        } else {
            return this.saveProduct(productDTO);
        }
    }

    @Override
    public ProductDTO getProduct(UUID id) {
        log.info("Запрос товара по ID: {}", id);
        return productRepository.findById(id)
                .map(product -> {
                    log.debug("Товар найден. Детали найденного товара: ID={}, название={}, цена={}",
                            product.getId(), product.getName(), product.getPrice());
                    log.info("Товар с ID={} успешно найден", id);
                    return productMapper.productToDto(product);
                })
                .orElseThrow(() -> {
                    log.error("Товар не найден: ID={}", id);
                    throw new ProductNotFoundException("Товар не найден с id: " + id);
                });
    }

    @Override
    public void deleteProduct(UUID id) {
        log.info("Удаление товара: ID={}", id);
        try {
            productRepository.deleteById(id);
            log.debug("Товар удалён: ID={}", id);
            log.info("Товар успешно удалён");
        } catch (Exception e) {
            log.error("Ошибка при удалении товара: ID={}", id, e);
            throw e;
        }
    }

    @Override
    public ProductDTO applyPatchToProduct(UUID id, JsonNode patchNode) {
        log.info("Применение JSON Patch к товару с ID: {}", id);
        log.debug("Входящий JSON Patch: {}", patchNode);
        try {
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException("Товар не найден с id: " + id));

            ProductDTO productDTO = productMapper.productToDto(existingProduct);
            log.debug("Исходные данные о продукте: {}", productDTO);

            ObjectMapper mapper = new ObjectMapper();
            JsonPatch patch = JsonPatch.fromJson(patchNode);
            JsonNode patchedNode = patch.apply(mapper.valueToTree(productDTO));

            ProductDTO patchedProductDTO = mapper.treeToValue(patchedNode, ProductDTO.class);
            log.debug("Исправлены данные о продукте: {}", patchedProductDTO);

            productMapper.updateProductFromDto(patchedProductDTO, existingProduct);

            Product updatedProduct = productRepository.save(existingProduct);
            log.debug("Продукт успешно обновлен: {}", updatedProduct);
            return productMapper.productToDto(updatedProduct);

        } catch (JsonPatchException e) {
            throw new ProductUpdateException("Ошибка применения JSON Patch", e);
        } catch (IOException e) {
            throw new ProductUpdateException("Ошибка преобразования JSON", e);
        }
    }


    public static String selectorSortField(String sortType) {
        String sortField;
        switch (sortType) {
            case "reviews":
                sortField = "reviewCount";
                break;
            case "rating":
                sortField = "averageRating";
                break;
            case "newest":
                sortField = "createdAt";
                break;
            case "price":
                sortField = "price";
                break;
            default:
                sortField = "price";
        }
        return sortField;
    }
}
