--liquibase formatted sql

--changeset sungurov-pb:DDL-1.2
CREATE TABLE IF NOT EXISTS ecommerce.categories (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    createdAt TIMESTAMP DEFAULT NOW(),
    updatedAt TIMESTAMP DEFAULT NOW()
    );
--rollback DROP TABLE ecommerce.categories;

--changeset sungurov-pb:DDL-1.3
CREATE TABLE IF NOT EXISTS ecommerce.products (
    id INT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price INT NOT NULL,
    categoryId INT NOT NULL REFERENCES ecommerce.categories(id),
    createdAt TIMESTAMP DEFAULT NOW(),
    updatedAt TIMESTAMP DEFAULT NOW()
    );
--rollback DROP TABLE ecommerce.products;

--changeset sungurov-pb:DDL-1.4
CREATE TABLE IF NOT EXISTS ecommerce.reviews (
    id INT PRIMARY KEY,
    productId INT NOT NULL REFERENCES ecommerce.products(id),
    userId INT NOT NULL,
    reviewText TEXT,
    rating INT,
    createdAt TIMESTAMP DEFAULT NOW(),
    updatedAt TIMESTAMP DEFAULT NOW()
    );
--rollback DROP TABLE ecommerce.reviews;