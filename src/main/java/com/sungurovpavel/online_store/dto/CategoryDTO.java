package com.sungurovpavel.online_store.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

@Value
@Builder
public class CategoryDTO {
    UUID id;
    String name;
    String description;
    Date createdAt;
}
