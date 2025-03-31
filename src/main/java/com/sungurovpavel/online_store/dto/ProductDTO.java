package com.sungurovpavel.online_store.dto;

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
     int price;
     CategoryDTO category; // Вложенный DTO
     List<ReviewDTO> reviews; // Вложенный DTO
     Date createdAt;
     Date updatedAt;
}