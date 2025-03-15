--liquibase formatted sql

--changeset sungurov-pb:DCL-2.0
GRANT SELECT, INSERT, UPDATE, DELETE ON ecommerce.products TO product_service_user;
--rollback REVOKE SELECT, INSERT, UPDATE, DELETE ON ecommerce.products FROM product_service_user;

--changeset sungurov-pb:DCL-2.1
GRANT SELECT ON ecommerce.categories TO product_service_user;
--rollback REVOKE SELECT ON ecommerce.categories FROM product_service_user;

--changeset sungurov-pb:DCL-2.2
GRANT SELECT, INSERT, UPDATE, DELETE ON ecommerce.reviews TO product_service_user;
--rollback REVOKE SELECT, INSERT, UPDATE, DELETE ON ecommerce.reviews FROM product_service_user;


--changeset sungurov-pb:DCL-2.3
GRANT USAGE ON SCHEMA ecommerce TO product_service_user;
--rollback REVOKE USAGE ON SCHEMA ecommerce FROM product_service_user;