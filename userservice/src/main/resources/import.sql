INSERT INTO USERS (id, first_name, last_name, email, phone) VALUES (1, 'John', 'Doe', 'john.doe@example.com', '+12345678901');

INSERT INTO USERS (id, first_name, last_name, email, phone) VALUES (2, 'Jane', 'Smith', 'jane.smith@example.com', '+12345678902');

INSERT INTO USERS (id, first_name, last_name, email, phone) VALUES (3, 'Alice', 'Johnson', 'alice.johnson@example.com', '+12345678903');

INSERT INTO USERS (id, first_name, last_name, email, phone) VALUES (4, 'Bob', 'Brown', 'bob.brown@example.com', '+12345678904');

INSERT INTO USERS (id, first_name, last_name, email, phone) VALUES (5, 'Charlie', 'Davis', 'charlie.davis@example.com', '+12345678905');

ALTER TABLE USERS ALTER COLUMN id RESTART WITH 6;