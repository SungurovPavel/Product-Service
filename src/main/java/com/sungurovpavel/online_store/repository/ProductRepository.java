package com.sungurovpavel.online_store.repository;

import com.sungurovpavel.online_store.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
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




}
