package com.sungurovpavel.online_store.dto.mapper;

import com.sungurovpavel.online_store.dto.ReviewDTO;
import com.sungurovpavel.online_store.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "userId", source = "userId")
    Review toEntity(ReviewDTO reviewDTO);

    @Mapping(target = "userId", source = "userId")
    ReviewDTO reviewToDto(Review review);
}
