--liquibase formatted sql

--changeset sungurov-pb:DCL-2.4
REVOKE SELECT, INSERT, UPDATE, DELETE ON ecommerce.products FROM product_service_user;
--rollback GRANT SELECT, INSERT, UPDATE, DELETE ON ecommerce.products TO product_service_user;

--changeset sungurov-pb:DCL-2.5
REVOKE SELECT ON ecommerce.categories FROM product_service_user;
--rollback GRANT SELECT ON ecommerce.categories TO product_service_user;

--changeset sungurov-pb:DCL-2.6
REVOKE SELECT, INSERT, UPDATE, DELETE ON ecommerce.reviews FROM product_service_user;
--rollback GRANT SELECT, INSERT, UPDATE, DELETE ON ecommerce.reviews TO product_service_user;

--changeset sungurov-pb:DCL-2.7
REVOKE USAGE ON SCHEMA ecommerce FROM product_service_user;
--rollback GRANT USAGE ON SCHEMA ecommerce TO product_service_user;