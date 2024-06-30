-- V1__Orders.sql
-- Migration script for creating the orders and ordered_products tables

-- Create the orders table
CREATE TABLE orders (
    order_id BIGINT PRIMARY KEY,
    user_id BIGINT
);

-- Create sequence for orders table
CREATE SEQUENCE order_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Create the ordered_products table
CREATE TABLE ordered_products (
    product_id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(19, 2) NOT NULL,
    quantity INTEGER NOT NULL,
    stock_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

-- Create sequence for ordered_products table
CREATE SEQUENCE ordered_product_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
