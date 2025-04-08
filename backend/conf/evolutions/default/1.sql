-- This is the first evolution script

-- --- !Ups

CREATE TABLE product
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    code     VARCHAR(255) NOT NULL,
    price    DECIMAL      NOT NULL,
    details  TEXT
);

-- --- !Downs

DROP TABLE product;