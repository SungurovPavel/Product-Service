package com.sungurovpavel.online_store.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Value
@Builder
public class ProductDTO {
     UUID id;
     String name;
     String description;

     @NotNull

     int price;

     CategoryDTO category;
     List<ReviewDTO> reviews;
     Date createdAt;
     Date updatedAt;
}