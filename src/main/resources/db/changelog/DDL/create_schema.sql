--liquibase formatted sql

--changeset Pavel_Sungurov:9
CREATE SCHEMA IF NOT EXISTS ecommerce;
--rollback DROP SCHEMA IF EXISTS ecommerce CASCADE;

