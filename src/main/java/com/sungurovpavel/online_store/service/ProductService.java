package com.sungurovpavel.online_store.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.sungurovpavel.online_store.dto.ProductDTO;
import com.sungurovpavel.online_store.dto.ResponseProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    public ProductDTO saveProduct(ProductDTO productDTO); // создание нового товара или изменение уже имеющегося товара

    public ProductDTO saveProduct(UUID id, ProductDTO productDTO);

    public ProductDTO getProduct(UUID id); // возвращает конкретный товар по его ID

    ProductDTO applyPatchToProduct(UUID id, JsonNode patchNode);

    public void deleteProduct(UUID id); // удаляет товар по его ID

    public ResponseProductDTO getProductsByFilterAndSort(List<String> categoryNames, Integer minPrice, Integer maxPrice, String searchTerm,
                                                         String sortType, String sortDirection, Integer page, Integer size); //возвращает товары по фильтру (от минимальной до максимальной цены, по категории, по совпадению в название) и сортируют их
}