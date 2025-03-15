--liquibase formatted sql

--changeset sungurov-pb:DDL-1.0
CREATE SCHEMA IF NOT EXISTS ecommerce;
--rollback DROP SCHEMA IF EXISTS ecommerce CASCADE;


--changeset sungurov-pb:DDL-1.1
CREATE ROLE product_service_user;
--rollback DROP ROLE IF EXISTS product_service_user;
