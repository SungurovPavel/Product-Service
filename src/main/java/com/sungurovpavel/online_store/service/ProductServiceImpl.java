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
        log.debug("Fetching products page {}, size {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<Product> products = productRepository.findAll(pageable);
        log.info("Found {} products on page {}", products.getNumberOfElements(), pageable.getPageNumber());
        return products.map(product -> productMapper.productToDto(product));
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        log.info("Saving product: {}", productDTO.getName());
        try {
            Product product = productMapper.toEntity(productDTO);
            Product savedProduct = productRepository.save(product);
            log.debug("Product saved with ID: {}", savedProduct.getId());
            return productMapper.productToDto(savedProduct);
        } catch (Exception e) {
            log.error("Error saving product: {}", productDTO.getName(), e);
            throw e;
        }
    }

    @Override
    public ProductDTO getProduct(UUID id) {
        log.debug("Fetching product with ID: {}", id);
        return productRepository.findById(id)
                .map(product -> {
                    log.debug("Found product: {}", product.getName());
                    return productMapper.productToDto(product);
                })
                .orElseThrow(() -> {
                    log.warn("Product not found with ID: {}", id);
                    return new EntityNotFoundException("Product not found with id: " + id);
                });
    }

    @Override
    public void deleteProduct(UUID id) {
        log.info("Deleting product with ID: {}", id);
        try {
            productRepository.deleteById(id);
            log.debug("Product deleted successfully: {}", id);
        } catch (Exception e) {
            log.error("Error deleting product with ID: {}", id, e);
            throw e;
        }
    }
}
