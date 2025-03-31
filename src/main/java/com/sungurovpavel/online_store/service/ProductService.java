package com.sungurovpavel.online_store.service;

import com.sungurovpavel.online_store.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {



    public ProductDTO saveProduct(ProductDTO productDTO); // создание нового товара или изменение уже имеющегося товара
    public ProductDTO getProduct(UUID id); // возвращает конкретный товар по его ID
    public void deleteProduct(UUID id); // удаляет товар по его ID

    Page<ProductDTO> getAllProducts(Pageable pageable); // возвращает все товары

}
