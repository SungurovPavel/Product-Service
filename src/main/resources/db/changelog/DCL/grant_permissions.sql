--liquibase formatted sql

--changeset sungurov-pb:DCL-2.0
GRANT SELECT, INSERT, UPDATE, DELETE ON ecommerce.products TO productServiceUser;
--rollback REVOKE SELECT, INSERT, UPDATE, DELETE ON ecommerce.products FROM productServiceUser;

--changeset sungurov-pb:DCL-2.1
GRANT SELECT ON ecommerce.categories TO productServiceUser;
--rollback REVOKE SELECT ON ecommerce.categories FROM productServiceUser;

--changeset sungurov-pb:DCL-2.2
GRANT SELECT, INSERT, UPDATE, DELETE ON ecommerce.reviews TO productServiceUser;
--rollback REVOKE SELECT, INSERT, UPDATE, DELETE ON ecommerce.reviews FROM productServiceUser;


--changeset sungurov-pb:DCL-2.3
GRANT USAGE ON SCHEMA ecommerce TO productServiceUser;
--rollback REVOKE USAGE ON SCHEMA ecommerce FROM productServiceUser;