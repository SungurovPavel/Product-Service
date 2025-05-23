package com.sungurovpavel.online_store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sungurovpavel.online_store.dto.ProductDTO;
import com.sungurovpavel.online_store.dto.ResponseProductDTO;
import com.sungurovpavel.online_store.exception.IdMismatchException;
import com.sungurovpavel.online_store.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private UUID testId;
    private ProductDTO testProductDTO;


    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        testProductDTO = ProductDTO.builder()
                .id(testId)
                .name("Test Product")
                .description("Test Description")
                .price(100)
                .build();
    }

    @Test
    void getAllWithFilterAndSortProducts_ShouldReturnResponseProductDTO() {

        List<String> categories = List.of("Electronics", "Books");
        Integer minPrice = 10;
        Integer maxPrice = 1000;
        String searchTerm = "test";
        int page = 0;
        int size = 10;
        String sortType = "price";
        String sortDirection = "asc";

        ResponseProductDTO expectedResponse = ResponseProductDTO.builder().build();
        when(productService.getProductsByFilterAndSort(categories, minPrice, maxPrice, searchTerm,
                sortType, sortDirection, page, size)).thenReturn(expectedResponse);

        ResponseProductDTO response = productController.getAllWithFilterAndSortProducts(
                categories, minPrice, maxPrice, searchTerm, page, size, sortType, sortDirection);

        assertSame(expectedResponse, response);
        verify(productService).getProductsByFilterAndSort(categories, minPrice, maxPrice, searchTerm,
                sortType, sortDirection, page, size);
    }

    @Test
    void getProduct_ShouldReturnProductDTO() {

        when(productService.getProduct(testId)).thenReturn(testProductDTO);

        ProductDTO result = productController.getProduct(testId);

        assertSame(testProductDTO, result);
        verify(productService).getProduct(testId);
    }

    @Test
    void addNewProduct_ShouldReturnCreatedProduct() {
        when(productService.saveProduct(testProductDTO)).thenReturn(testProductDTO);

        ProductDTO result = productController.addNewProduct(testProductDTO);

        assertSame(testProductDTO, result);
        verify(productService).saveProduct(testProductDTO);
    }

    @Test
    void updateProduct_WhenIdsMatch_ShouldReturnUpdatedProduct() {

        when(productService.saveProduct(testId, testProductDTO)).thenReturn(testProductDTO);

        ProductDTO result = productController.updateProduct(testId, testProductDTO);

        assertSame(testProductDTO, result);
        verify(productService).saveProduct(testId, testProductDTO);
    }

    @Test
    void updateProduct_WhenIdsDoNotMatch_ShouldThrowException() {

        UUID differentId = UUID.randomUUID();
        testProductDTO = ProductDTO.builder().id(differentId).build();

        assertThrows(IdMismatchException.class, () -> {
            productController.updateProduct(testId, testProductDTO);
        });

        verify(productService, never()).saveProduct(any(), any());
    }

    @Test
    void deleteProduct_ShouldCallService() {

        productController.deleteProduct(testId);

        verify(productService).deleteProduct(testId);
    }

/*    @Test
    void partialUpdateProduct_ShouldReturnUpdatedProduct() throws Exception {
        String patchJson = "[{\"op\":\"replace\", \"path\":\"/name\", \"value\":\"Новое название\"}]";
        JsonNode patchNode = mapper.readTree(patchJson);

        when(productService.applyPatchToProduct(testId, patchNode))
                .thenReturn(testProductDTO);

        ProductDTO result = productController.partialUpdateProduct(testId, patchNode);

        assertSame(testProductDTO, result);
        verify(productService).applyPatchToProduct(testId, patchNode);


    }

    @Test
    void partialUpdateProduct_ShouldThrowOnInvalidPatch() throws JsonProcessingException {
        String invalidPatchJson = "[{\"op\":\"invalid\", \"path\":\"/name\"}]";
        JsonNode invalidPatch = mapper.readTree(invalidPatchJson);

        when(productService.applyPatchToProduct(testId, invalidPatch))
                .thenThrow(new JsonPatchException("Invalid operation"));

        assertThrows(JsonPatchException.class, () ->
                productController.partialUpdateProduct(testId, invalidPatch));
    }

    @Test
    void partialUpdateProduct_ShouldThrowIfProductNotFound() throws JsonProcessingException {
        JsonNode patchNode = mapper.readTree("[{\"op\":\"replace\", ... }]");

        when(productService.applyPatchToProduct(testId, patchNode))
                .thenThrow(new ProductNotFoundException("Товар не найден"));

        assertThrows(ProductNotFoundException.class, () ->
                productController.partialUpdateProduct(testId, patchNode));
    }*/


}
