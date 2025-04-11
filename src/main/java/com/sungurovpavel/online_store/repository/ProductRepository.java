package com.sungurovpavel.online_store.repository;

import com.sungurovpavel.online_store.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @EntityGraph(attributePaths = {"category", "reviews"})
    List<Product> findAll();

    @EntityGraph(attributePaths = {"category", "reviews"})
    Optional<Product> findById(UUID id);

    /*
    Метод для фильтрации (по цене, категории, по совпадению в название) и сортировке (по цене, кол-ву отзывов,
    рейтингу, новизне). Дефолтная сортировка по кол-ву отзывов и по убыванию (DESC).
    */
    @EntityGraph(attributePaths = {"category", "reviews"})
    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:searchTerm IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', CAST(:searchTerm AS string), '%')))" +
            "ORDER BY " +
            "CASE WHEN :sortType = 'price' AND :sortDirection = 'asc' THEN p.price END ASC, " +
            "CASE WHEN :sortType = 'price' AND :sortDirection = 'desc' THEN p.price END DESC, " +
            "CASE WHEN :sortType = 'rating' AND :sortDirection = 'asc' THEN (SELECT COALESCE(AVG(r.rating), 0) FROM p.reviews r) END ASC, " +
            "CASE WHEN :sortType = 'rating' AND :sortDirection = 'desc' THEN (SELECT COALESCE(AVG(r.rating), 0) FROM p.reviews r) END DESC, " +
            "CASE WHEN :sortType = 'newest' AND :sortDirection = 'asc' THEN p.createdAt END ASC, " +
            "CASE WHEN :sortType = 'newest' AND :sortDirection = 'desc' THEN p.createdAt END DESC, " +
            "CASE WHEN :sortType = 'reviews' AND :sortDirection = 'asc' THEN SIZE(p.reviews) END ASC, " +
            "CASE WHEN :sortType = 'reviews' AND :sortDirection = 'desc' THEN SIZE(p.reviews) END DESC " )
    Page<Product> findByFiltersWithSorts(
            @Param("categoryId") UUID categoryId,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("searchTerm") String searchTerm,
            @Param("sortType") String sortType,
            @Param("sortDirection") String sortDirection,
            Pageable pageable);
}


