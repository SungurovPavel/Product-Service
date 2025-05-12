package com.sungurovpavel.online_store.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name ="products", schema = "ecommerce")
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Review> reviews;

    @Formula("(SELECT COUNT(r.id) FROM ecommerce.reviews r WHERE r.product_id = id)")
    private Long reviewCount;

    @Formula("(SELECT COALESCE(AVG(r.rating), 0) FROM ecommerce.reviews r WHERE r.product_id = id)")
    private Double averageRating;

    public Product() {
    }

    public Product(UUID id, String name, String description, int price, Category category, Date createdAt, Date updatedAt, List<Review> reviews) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.reviews = reviews;
    }
}
