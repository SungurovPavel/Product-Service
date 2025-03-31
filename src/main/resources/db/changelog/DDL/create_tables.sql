--liquibase formatted sql

--changeset sungurov-pb:DDL-1.2
CREATE TABLE IF NOT EXISTS ecommerce.categories (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
    );
--rollback DROP TABLE ecommerce.categories;

--changeset sungurov-pb:DDL-1.3
CREATE TABLE IF NOT EXISTS ecommerce.products (
    id UUID PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price INT NOT NULL,
    category_id UUID NOT NULL REFERENCES ecommerce.categories(id),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
    );
--rollback DROP TABLE ecommerce.products;

--changeset sungurov-pb:DDL-1.4
CREATE TABLE IF NOT EXISTS ecommerce.reviews (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL REFERENCES ecommerce.products(id),
    userId UUID NOT NULL,
    review_text TEXT,
    rating INT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
    );
--rollback DROP TABLE ecommerce.reviews;