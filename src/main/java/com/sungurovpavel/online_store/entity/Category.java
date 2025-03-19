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
@Table(name ="Categories")
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Date created_at;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updated_at;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Product> products;

    public Category() {
    }

    public Category(int id, String name, String description, Date created_at, Date updated_at, List<Product> products) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.products = products;
    }
}
