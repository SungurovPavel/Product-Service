package com.sungurovpavel.online_store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;


@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "reviews", schema = "ecommerce")
public class Review {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "userid", nullable = false)
    private UUID userId;

    @Column(name = "review_text")
    private String reviewText;

    @Column(name = "rating")
    private int rating;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    public Review() {
    }

    public Review(UUID id, Product product, UUID userId, String reviewText, int rating, Date createdAt, Date updatedAt) {
        this.id = id;
        this.product = product;
        this.userId = userId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
