CREATE TABLE IF NOT EXISTS Customers (
    id SERIAL PRIMARY KEY,
    firstName VARCHAR(250) NOT NULL,
    lastName VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL UNIQUE,
    phone VARCHAR(250) NOT NULL,
    passkey VARCHAR(250) NOT NULL,
    previous_orders INTEGER[],
    rewards_points INTEGER DEFAULT 0
);