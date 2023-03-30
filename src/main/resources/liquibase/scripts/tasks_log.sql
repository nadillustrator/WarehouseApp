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
