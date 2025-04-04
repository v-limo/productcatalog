CREATE TABLE products
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    code     VARCHAR(50)  NOT NULL,
    price    DECIMAL      NOT NULL
);
