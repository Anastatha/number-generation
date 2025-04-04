--liquibase formatted sql
-- changeset author:create-role-enum

CREATE TYPE user_role AS ENUM ('USER', 'ADMIN');
