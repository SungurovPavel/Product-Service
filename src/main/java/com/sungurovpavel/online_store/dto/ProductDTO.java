package com.sungurovpavel.online_store.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class ProductDTO {
    UUID id;
    String name;
    String description;
    Integer price;
    CategoryDTO category;
    List<ReviewDTO> reviews;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    Date updatedAt;
}