package com.sungurovpavel.online_store.service;

import com.sungurovpavel.online_store.dto.ProductDTO;
import com.sungurovpavel.online_store.dto.ResponseProductDTO;
import com.sungurovpavel.online_store.dto.mapper.ProductMapper;
import com.sungurovpavel.online_store.entity.Product;
import com.sungurovpavel.online_store.exception.InvalidPriceRangeException;
import com.sungurovpavel.online_store.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ResponseProductDTO getProductsByFilterAndSort(List<String> categoryNames, Integer minPrice, Integer maxPrice,
                                                         String searchTerm, String sortType, String sortDirection,
                                                         Pageable pageable) {

        log.info("Начало фильтрации товаров и сортировки");
        log.debug("Параметры: categoryNames={}, minPrice={}, maxPrice={}, searchTerm='{}', sortType='{}', sortDirection='{}', " +
                        "page={}, size={}",
                categoryNames, minPrice, maxPrice, searchTerm, sortType, sortDirection, pageable.getPageNumber(),
                pageable.getPageSize());
        try {
            if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
                throw new InvalidPriceRangeException("minPrice не может быть больше maxPrice");
            }

            if (categoryNames != null) {
                categoryNames = categoryNames.stream()
                        .map(String::toLowerCase)
                        .collect(Collectors.toList());
            }

            //StringBuilder sb = new StringBuilder();
            //sb.append(sortType).append(sortDirection);
            //String sort = sb.toString();

            Page<Product> products = productRepository.findByFiltersWithSorts(
                    categoryNames, minPrice, maxPrice, searchTerm, sortType, sortDirection, pageable);
            Page<ProductDTO> productDTOs = products.map(product -> productMapper.productToDto(product));
            ResponseProductDTO response = ResponseProductDTO.builder()
                    .products(productDTOs.getContent())
                    .totalPages(products.getTotalPages())
                    .currentPage(products.getNumber())
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
                    return new EntityNotFoundException("Товар не найден с id: " + id);
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
}
