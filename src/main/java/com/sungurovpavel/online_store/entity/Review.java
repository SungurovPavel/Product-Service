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
    @JoinColumn(name = "product_id")
    private  Product product;

    @Column(name = "user_id")
    private int user_id;


    @Column(name = "review_text")
    private String review_text;


    @Column(name = "rating")
    private int rating;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Date created_at;


    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updated_at;




    public Review() {
    }

    public Review(int id, Product product, int user_id, String review_text, int rating, Date created_at, Date updated_at) {
        this.id = id;
        this.product = product;
        this.user_id = user_id;
        this.review_text = review_text;
        this.rating = rating;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
