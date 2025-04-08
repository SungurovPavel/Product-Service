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
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        log.info("Запрос на получение списка товаров (страница {}, размер {})", pageable.getPageNumber(), pageable.getPageSize());
        Page<Product> products = productRepository.findAll(pageable);
        log.debug("Получено {} товаров на странице {}", products.getNumberOfElements(), pageable.getPageNumber());
        log.info("Список товаров успешно возвращён");
        return products.map(product -> productMapper.productToDto(product));
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
