--liquibase formatted sql

--changeset sandis:1


CREATE TABLE saved_url
(
    id             serial PRIMARY KEY,
    full_url       text NOT NULL,
    short_url      text NOT NULL UNIQUE,
    times_consumed int,
    expires        DATE
)

