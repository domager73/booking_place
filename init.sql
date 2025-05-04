CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       full_name VARCHAR(100) NOT NULL,
                       role VARCHAR(20) NOT NULL DEFAULT 'user' CHECK (role IN ('user', 'admin'))
);

CREATE TABLE workspaces (
                            workspace_id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL
);

CREATE TABLE bookings (
                          booking_id SERIAL PRIMARY KEY,
                          user_id INTEGER REFERENCES users(user_id) NOT NULL,
                          workspace_id INTEGER REFERENCES workspaces(workspace_id) NOT NULL,
                          start_time TIMESTAMP WITH TIME ZONE NOT NULL,
                          end_time TIMESTAMP WITH TIME ZONE NOT NULL
);