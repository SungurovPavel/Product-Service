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
    Optional<Product> findById(UUID id);


    @EntityGraph(attributePaths = {"category","reviews"})
    @Query("""
            
                SELECT p FROM Product p  WHERE
                        (COALESCE(:categoryNames, NULL) IS NULL OR LOWER(p.category.name) IN :categoryNames) AND
                        (:minPrice IS NULL OR p.price >= :minPrice) AND
                        (:maxPrice IS NULL OR p.price <= :maxPrice) AND
                        (:searchTerm IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', CAST(:searchTerm AS string), '%')))
            """)
    Page<Product> findByFiltersWithSorts(
            @Param("categoryNames") List<String> categoryNames,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("searchTerm") String searchTerm,
            Pageable pageable);
}


