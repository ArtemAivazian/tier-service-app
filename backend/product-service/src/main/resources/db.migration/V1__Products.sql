-- CREATE SEQUENCES
CREATE SEQUENCE IF NOT EXISTS stock_sequence START 1;
CREATE SEQUENCE IF NOT EXISTS product_sequence START 1;

-- CREATE TABLES
CREATE TABLE IF NOT EXISTS stocks (
    stock_id SERIAL PRIMARY KEY,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    product_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price NUMERIC(38,2) NOT NULL,
    quantity INTEGER NOT NULL,
    stock_id BIGINT NOT NULL,
    CONSTRAINT fk_product_stock
        FOREIGN KEY (stock_id)
        REFERENCES stocks(stock_id)
);