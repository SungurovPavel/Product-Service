package com.sungurovpavel.online_store.controller;

import com.sungurovpavel.online_store.dto.ReviewDTO;
import com.sungurovpavel.online_store.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products/{productId}/reviews")
@RequiredArgsConstructor
@Tag(name = "Review API", description = "Управление отзывами")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(
            summary = "Добавить отзыв к товару",
            description = "Добавляет новый отзыв к указанному товару",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Пример запроса",
                                            value = """
                                                    {
                                                        "reviewText": "Крутой товар!",
                                                        "rating": 5,
                                                        "userId": "49eebc99-9c0b-4ef8-bb6d-6bb9bd380a23"
                                                    }
                                                    """
                                    )
                            },
                            schema = @Schema(implementation = ReviewDTO.class)
                    )
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDTO addReview(
            @Parameter(description = "ID товара", required = true)
            @PathVariable UUID productId,
            @RequestBody ReviewDTO reviewDTO) {

        return reviewService.addReview(productId, reviewDTO);
    }
}