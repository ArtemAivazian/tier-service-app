-- Create sequence for user_id
CREATE SEQUENCE IF NOT EXISTS user_sequence START 1;

-- Create the users table if it doesn't exist
CREATE TABLE IF NOT EXISTS users (
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(120) NOT NULL UNIQUE,
    encrypted_password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    role VARCHAR(255) CHECK (role IN ('ADMIN', 'USER'))
);

-- Insert user if not exists
INSERT INTO users (email, encrypted_password, first_name, last_name, role)
VALUES ('aivazart@fel.cvut.cz', '$2a$04$g3A8zQaS5pykydEVitBz4uTT9.NOclXiFUGaQFLfQF/wjXdQ7AIpu', 'Artem', 'Aivazian', 'ADMIN')
ON CONFLICT (email) DO NOTHING;
