package com.sungurovpavel.online_store.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@Entity
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


    @Column(name = "created_at")
    private Date created_at;



    @Column(name = "updated_at")
    private Date updated_at;




    public Review() {
    }

    public Review(int user_id, String review_text, int rating, Date created_at, Date updated_at) {
        this.user_id = user_id;
        this.review_text = review_text;
        this.rating = rating;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", review_text='" + review_text + '\'' +
                ", rating=" + rating +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
