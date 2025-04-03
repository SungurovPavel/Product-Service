package com.sungurovpavel.online_store.dto;

import lombok.Builder;
import lombok.Value;
import java.util.Date;
import java.util.UUID;

@Value
@Builder
public class ReviewDTO {
    UUID id;
    String reviewText;
    int rating;
    Date createdAt;
    // Не включил Product, чтобы избежать рекурсии
}