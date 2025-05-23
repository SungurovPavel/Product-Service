package com.sungurovpavel.online_store.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class ReviewDTO {
    UUID id;

    @Schema(description = "Текст отзыва", example = "Крутой товар!")
    String reviewText;

    @Schema(description = "Рейтинг (1-5)", example = "5")
    Integer rating;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    Date createdAt;

    @Schema(description = "ID пользователя",
            example = "49eebc99-9c0b-4ef8-bb6d-6bb9bd380a23",
            format = "uuid")
    UUID userId;
}