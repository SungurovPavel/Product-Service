-- Вставка категорий
INSERT INTO ecommerce.categories (id, name, description, created_at, updated_at)
VALUES
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Electronics', 'Gadgets and devices', NOW(), NOW()),
    ('b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Clothing', 'Apparel and accessories', NOW(), NOW()),
    ('c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Home', 'Home and kitchen products', NOW(), NOW());

-- Вставка продуктов (с привязкой к категориям)
INSERT INTO ecommerce.products (id, name, description, price, category_id, created_at, updated_at)
VALUES
    ('d3eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'Smartphone X', 'Latest smartphone model', 999, 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', NOW(), NOW()),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'Laptop Pro', 'High-performance laptop', 1499, 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', NOW(), NOW()),
    ('f5eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'Cotton T-Shirt', 'Comfortable cotton shirt', 29, 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', NOW(), NOW()),
    ('06eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', 'Blender', 'Powerful kitchen blender', 89, 'c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', NOW(), NOW());

-- Вставка отзывов (с привязкой к продуктам)
INSERT INTO ecommerce.reviews (id, product_id, userId, review_text, rating, created_at, updated_at)
VALUES
    ('17eebc99-9c0b-4ef8-bb6d-6bb9bd380a18', 'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', '27eebc99-9c0b-4ef8-bb6d-6bb9bd380a21', 'Great phone! Fast delivery.', 5, NOW(), NOW()),
    ('28eebc99-9c0b-4ef8-bb6d-6bb9bd380a19', 'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', '38eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 'Battery could be better', 4, NOW(), NOW()),
    ('39eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', '49eebc99-9c0b-4ef8-bb6d-6bb9bd380a23', 'Perfect for work and gaming', 5, NOW(), NOW()),
    ('40eebc99-9c0b-4ef8-bb6d-6bb9bd380a21', 'f5eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', '50eebc99-9c0b-4ef8-bb6d-6bb9bd380a24', 'Very comfortable material', 5, NOW(), NOW());