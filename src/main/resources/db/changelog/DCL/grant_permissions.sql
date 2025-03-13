--liquibase formatted sql

-- Предоставление прав на таблицу products
--changeset Pavel_Sungurov:1
GRANT SELECT, INSERT, UPDATE, DELETE ON ecommerce.products TO product_service_user;
--rollback REVOKE SELECT, INSERT, UPDATE, DELETE ON ecommerce.products FROM product_service_user;

-- Предоставление прав на таблицу categories
--changeset Pavel_Sungurov:2
GRANT SELECT ON ecommerce.categories TO product_service_user;
--rollback REVOKE SELECT ON ecommerce.categories FROM product_service_user;

-- Предоставление прав на таблицу reviews
--changeset Pavel_Sungurov:3
GRANT SELECT, INSERT, UPDATE, DELETE ON ecommerce.reviews TO product_service_user;
--rollback REVOKE SELECT, INSERT, UPDATE, DELETE ON ecommerce.reviews FROM product_service_user;

-- Предоставление прав на использование схемы ecommerce
--changeset Pavel_Sungurov:4
GRANT USAGE ON SCHEMA ecommerce TO product_service_user;
--rollback REVOKE USAGE ON SCHEMA ecommerce FROM product_service_user;