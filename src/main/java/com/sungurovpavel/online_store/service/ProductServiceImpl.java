package com.sungurovpavel.online_store.service;

import com.sungurovpavel.online_store.dto.ProductDTO;
import com.sungurovpavel.online_store.dto.mapper.ProductMapper;
import com.sungurovpavel.online_store.repository.ProductRepository;
import com.sungurovpavel.online_store.entity.Product;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductDTO> getProductsByFilterAndSort(UUID categoryId, Integer minPrice, Integer maxPrice, String searchTerm,
                                                       String sortType, String sortDirection, Pageable pageable) {

        log.info("Начало фильтрации товаров и сортировки");
        log.debug("Параметры: categoryId={}, minPrice={}, maxPrice={}, searchTerm='{}', sortType='{}', sortDirection='{}', page={}, size={}",
                categoryId, minPrice, maxPrice, searchTerm, sortType, sortDirection, pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<Product> products = productRepository.findByFiltersWithSorts(
                    categoryId, minPrice, maxPrice, searchTerm, sortType, sortDirection, pageable);
            log.debug("Результаты фильтрации: найдено {} товаров, всего страниц {}",
                    products.getNumberOfElements(), products.getTotalPages());
            log.info("Фильтрация товаров и сортировка завершена успешно");
            return products.map(product -> productMapper.productToDto(product));
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
