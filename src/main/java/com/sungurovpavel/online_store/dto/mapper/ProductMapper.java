package com.sungurovpavel.online_store.dto.mapper;

import com.sungurovpavel.online_store.dto.CategoryDTO;
import com.sungurovpavel.online_store.dto.ProductDTO;
import com.sungurovpavel.online_store.dto.ReviewDTO;
import com.sungurovpavel.online_store.entity.Category;
import com.sungurovpavel.online_store.entity.Product;
import com.sungurovpavel.online_store.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category", source = "category")
    @Mapping(target = "reviews", source = "reviews")
    ProductDTO productToDto(Product product);

    List<ProductDTO> toDtoList(List<Product> products);

    CategoryDTO categoryToDto(Category category);

    ReviewDTO reviewToDto(Review review);

    @Mapping(target = "reviews", ignore = true)
    Product toEntity(ProductDTO productDTO);

}