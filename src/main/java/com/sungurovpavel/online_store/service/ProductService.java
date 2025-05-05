package com.sungurovpavel.online_store.service;

import com.sungurovpavel.online_store.dto.ProductDTO;
import com.sungurovpavel.online_store.dto.ResponseProductDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDTO saveProduct(ProductDTO productDTO); // создание нового товара или изменение уже имеющегося товара
    ProductDTO getProduct(UUID id); // возвращает конкретный товар по его ID
    void deleteProduct(UUID id); // удаляет товар по его ID
    ResponseProductDTO getProductsByFilterAndSort(List<String> categoryNames, Integer minPrice, Integer maxPrice, String searchTerm,
                                                         String sortType, String sortDirection, int page, int size); //возвращает товары по фильтру (от минимальной до максимальной цены, по категории, по совпадению в название) и сортируют их
}