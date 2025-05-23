package com.sungurovpavel.online_store.service;

import com.sungurovpavel.online_store.dto.ReviewDTO;
import com.sungurovpavel.online_store.dto.mapper.ReviewMapper;
import com.sungurovpavel.online_store.entity.Product;
import com.sungurovpavel.online_store.entity.Review;
import com.sungurovpavel.online_store.exception.ProductNotFoundException;
import com.sungurovpavel.online_store.repository.ProductRepository;
import com.sungurovpavel.online_store.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional
    public ReviewDTO addReview(UUID productId, ReviewDTO reviewDTO) {
        log.info("Добавление отзыва к товару с ID: {}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Товар не найден с id: " + productId));

        Review review = reviewMapper.toEntity(reviewDTO);
        review.setProduct(product);

        log.info("Saving review with userId: {}", reviewDTO.getUserId());
        Review savedReview = reviewRepository.save(review);
        log.debug("Отзыв сохранён: ID={}, товар={}, оценка={}",
                savedReview.getId(), productId, savedReview.getRating());

        return reviewMapper.reviewToDto(savedReview);
    }
}
