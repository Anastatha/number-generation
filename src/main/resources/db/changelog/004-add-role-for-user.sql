--liquibase formatted sql
-- changeset author:add-role-column

ALTER TABLE users
    ADD COLUMN role user_role NOT NULL DEFAULT 'USER';
