package com.sungurovpavel.online_store.service;

import com.sungurovpavel.online_store.dto.ProductDTO;
import com.sungurovpavel.online_store.dto.ResponseProductDTO;
import com.sungurovpavel.online_store.dto.mapper.ProductMapper;
import com.sungurovpavel.online_store.entity.Product;
import com.sungurovpavel.online_store.exception.InvalidPriceRangeException;
import com.sungurovpavel.online_store.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDTO productDTO;
    private UUID productId;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setPrice(100);

        productDTO = ProductDTO.builder()
                .id(productId)
                .name("Test Product")
                .price(100)
                .build();
    }

    @Test
    void getProductsByFilterAndSort_ShouldReturnFilteredProducts() {

        List<String> categories = List.of("electronics");
        int minPrice = 50;
        int maxPrice = 200;
        String searchTerm = "test";
        String sortType = "price";
        String sortDirection = "ASC";
        int page = 0;
        int size = 10;

        Page<Product> productPage = new PageImpl<>(List.of(product));
        when(productRepository.findByFiltersWithSorts(
                any(), any(), any(), any(), any(PageRequest.class))
        ).thenReturn(productPage);
        when(productMapper.productToDto(any(Product.class))).thenReturn(productDTO);


        ResponseProductDTO response = productService.getProductsByFilterAndSort(
                categories, minPrice, maxPrice, searchTerm, sortType, sortDirection, page, size);


        assertNotNull(response);
        assertEquals(1, response.getProducts().size());
        assertEquals(productDTO, response.getProducts().get(0));
        assertEquals(1, response.getTotalPages());
        assertEquals(1, response.getCurrentPage());
        assertEquals(1, response.getTotalElements());

        verify(productRepository).findByFiltersWithSorts(
                categories, minPrice, maxPrice, searchTerm,
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "price")));
    }

    @Test
    void getProductsByFilterAndSort_ShouldThrowExceptionWhenMinPriceGreaterThanMaxPrice() {

        int minPrice = 200;
        int maxPrice = 100;

        assertThrows(InvalidPriceRangeException.class, () ->
                productService.getProductsByFilterAndSort(
                        null, minPrice, maxPrice, null, "price", "ASC", 0, 10)
        );
    }

    @Test
    void getProductsByFilterAndSort_ShouldUseDefaultSortWhenInvalidSortTypeProvided() {

        Page<Product> productPage = new PageImpl<>(List.of(product));
        when(productRepository.findByFiltersWithSorts(
                any(), any(), any(), any(), any(PageRequest.class))
        ).thenReturn(productPage);
        when(productMapper.productToDto(any(Product.class))).thenReturn(productDTO);


        ResponseProductDTO response = productService.getProductsByFilterAndSort(
                null, null, null, null, "invalid", "ASC", 0, 10);


        verify(productRepository).findByFiltersWithSorts(
                eq(null), eq(null), eq(null), eq(null),
                argThat(pageable ->
                        pageable.getSort().getOrderFor("price") != null &&
                                pageable.getSort().getOrderFor("price").getDirection() == Sort.Direction.ASC
                ));
    }

    @Test
    void saveProduct_ShouldSaveAndReturnProductDTO() {

        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.productToDto(product)).thenReturn(productDTO);


        ProductDTO result = productService.saveProduct(productDTO);


        assertNotNull(result);
        assertEquals(productDTO, result);
        verify(productMapper).toEntity(productDTO);
        verify(productRepository).save(product);
        verify(productMapper).productToDto(product);
    }

    @Test
    void getProduct_ShouldReturnProductDTOWhenProductExists() {

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.productToDto(product)).thenReturn(productDTO);


        ProductDTO result = productService.getProduct(productId);


        assertNotNull(result);
        assertEquals(productDTO, result);
        verify(productRepository).findById(productId);
        verify(productMapper).productToDto(product);
    }

    @Test
    void getProduct_ShouldThrowEntityNotFoundExceptionWhenProductNotExists() {

        when(productRepository.findById(productId)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () ->
                productService.getProduct(productId)
        );
        verify(productRepository).findById(productId);
    }

    @Test
    void deleteProduct_ShouldDeleteProductWhenExists() {

        doNothing().when(productRepository).deleteById(productId);


        productService.deleteProduct(productId);


        verify(productRepository).deleteById(productId);
    }

    @Test
    void selectorSortField_ShouldReturnCorrectFieldName() {
        assertEquals("reviewCount", ProductServiceImpl.selectorSortField("reviews"));
        assertEquals("averageRating", ProductServiceImpl.selectorSortField("rating"));
        assertEquals("createdAt", ProductServiceImpl.selectorSortField("newest"));
        assertEquals("price", ProductServiceImpl.selectorSortField("price"));
        assertEquals("price", ProductServiceImpl.selectorSortField("invalid"));
    }
}