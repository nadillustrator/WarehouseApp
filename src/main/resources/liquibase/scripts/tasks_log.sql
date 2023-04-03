-- liquibase formatted sql

-- changeSet nadillustrator:1
CREATE TABLE socks
(
    id              SERIAL  NOT NULL PRIMARY KEY,
    color           TEXT NOT NULL,
    cotton_part     SMALLINT NOT NULL,
    quantity        NUMERIC
);

-- changeSet nadillustrator:2
ALTER TABLE socks ALTER COLUMN quantity TYPE BIGINT;
ALTER TABLE socks ALTER COLUMN quantity SET NOT NULL;

-- changeSet nadillustrator:3
CREATE TABLE income
(
    id              SERIAL  NOT NULL PRIMARY KEY,
    date_income     TIMESTAMP NOT NULL,
    socks_id        BIGINT NOT NULL
);
CREATE TABLE outcome
(
    id              SERIAL  NOT NULL PRIMARY KEY,
    date_outcome    TIMESTAMP NOT NULL,
    socks_id        BIGINT NOT NULL
);


