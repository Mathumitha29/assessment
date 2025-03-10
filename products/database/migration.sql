CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255),
    picture TEXT,
    price NUMERIC(10,2),
    description TEXT
);
