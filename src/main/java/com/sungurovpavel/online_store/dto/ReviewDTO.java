package com.sungurovpavel.online_store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

@Value
@Builder
public class ReviewDTO {
    UUID id;

    @Schema(description = "Текст отзыва", example = "Крутой товар!")
    String reviewText;

    @Schema(description = "Рейтинг (1-5)", example = "5")
    Integer rating;

    Date createdAt;

    @Schema(description = "ID пользователя",
            example = "49eebc99-9c0b-4ef8-bb6d-6bb9bd380a23",
            format = "uuid")
    UUID userId;
}