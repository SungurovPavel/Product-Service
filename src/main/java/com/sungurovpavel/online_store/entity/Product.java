package com.sungurovpavel.online_store.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;


@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name ="products")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;


    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private  Category category;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Date created_at;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updated_at;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Review> reviews;

    public Product() {
    }

    public Product(int id, String name, String description, int price, Category category, Date created_at, Date updated_at, List<Review> reviews) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.reviews = reviews;
    }
}
