--liquibase formatted sql

--changeset Pavel_Sungurov:10
CREATE TABLE IF NOT EXISTS ecommerce.categories (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
    );
--rollback DROP TABLE ecommerce.categories;

--changeset Pavel_Sungurov:11
CREATE TABLE IF NOT EXISTS ecommerce.products (
    id INT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price INT NOT NULL,
    category_id INT NOT NULL REFERENCES ecommerce.categories(id),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
    );
--rollback DROP TABLE ecommerce.products;

--changeset Pavel_Sungurov:12
CREATE TABLE IF NOT EXISTS ecommerce.reviews (
    id INT PRIMARY KEY,
    product_id INT NOT NULL REFERENCES ecommerce.products(id),
    user_id INT NOT NULL,
    review_text TEXT,
    rating INT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
    );
--rollback DROP TABLE ecommerce.reviews;