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

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(product -> productMapper.productToDto(product));
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.productToDto(savedProduct);
    }

    @Override
    public ProductDTO getProduct(UUID id) {
        return productRepository.findById(id)
                .map(product -> productMapper.productToDto(product))
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    @Override
    public void deleteProduct(UUID id) {
       productRepository.deleteById(id);
    }



}
