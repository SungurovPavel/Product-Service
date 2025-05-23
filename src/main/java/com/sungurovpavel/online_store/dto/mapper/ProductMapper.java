package com.sungurovpavel.online_store.dto.mapper;

import com.sungurovpavel.online_store.dto.CategoryDTO;
import com.sungurovpavel.online_store.dto.ProductDTO;
import com.sungurovpavel.online_store.entity.Category;
import com.sungurovpavel.online_store.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category", source = "category")
    @Mapping(target = "reviews", source = "reviews")
    ProductDTO productToDto(Product product);

    List<ProductDTO> toDtoList(List<Product> products);

    CategoryDTO categoryToDto(Category category);

    @Mapping(target = "reviews", ignore = true)
    Product toEntity(ProductDTO productDTO);

    Category toEntity(CategoryDTO categoryDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateProductFromDto(ProductDTO productDTO, @MappingTarget Product product);

}