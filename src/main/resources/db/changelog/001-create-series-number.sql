--liquibase formatted sql

-- changeset user:001
CREATE TABLE series_number (
                               id SERIAL PRIMARY KEY,
                               org_code VARCHAR(10) NOT NULL,
                               year VARCHAR(4) NOT NULL,
                               counter INTEGER NOT NULL DEFAULT 1
);