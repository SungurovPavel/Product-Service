package com.sungurovpavel.online_store.service;

import com.sungurovpavel.online_store.dto.ReviewDTO;

import java.util.UUID;

public interface ReviewService {
    ReviewDTO addReview(UUID productId, ReviewDTO reviewDTO);
}
