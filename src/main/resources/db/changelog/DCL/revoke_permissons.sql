--liquibase formatted sql

-- Отзыв прав на таблицу products
--changeset Pavel_Sungurov:5
REVOKE SELECT, INSERT, UPDATE, DELETE ON ecommerce.products FROM product_service_user;
--rollback GRANT SELECT, INSERT, UPDATE, DELETE ON ecommerce.products TO product_service_user;

-- Отзыв прав на таблицу products
--changeset Pavel_Sungurov:6
REVOKE SELECT ON ecommerce.categories FROM product_service_user;
--rollback GRANT SELECT ON ecommerce.categories TO product_service_user;

-- Отзыв прав на таблицу reviews
--changeset Pavel_Sungurov:7
REVOKE SELECT, INSERT, UPDATE, DELETE ON ecommerce.reviews FROM product_service_user;
--rollback GRANT SELECT, INSERT, UPDATE, DELETE ON ecommerce.reviews TO product_service_user;

-- Отзыв прав на использование схемы ecommerce
--changeset Pavel_Sungurov:8
REVOKE USAGE ON SCHEMA ecommerce FROM product_service_user;
--rollback GRANT USAGE ON SCHEMA ecommerce TO product_service_user;