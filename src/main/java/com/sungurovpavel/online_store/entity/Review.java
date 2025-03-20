package com.sungurovpavel.online_store.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;


@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name ="reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private  Product product;

    @Column(name = "userId")
    private int userId;

    @Column(name = "reviewText")
    private String reviewText;

    @Column(name = "rating")
    private int rating;

    @CreatedDate
    @Column(name = "createdAt", updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt")
    private Date updatedAt;

    public Review() {
    }

    public Review(int id, Product product, int userId, String reviewText, int rating, Date createdAt, Date updatedAt) {
        this.id = id;
        this.product = product;
        this.userId = userId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
