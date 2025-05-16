INSERT INTO FLIGHTS (id, flight_number, origin, destination, capacity, available_seats, price) VALUES (1, 'AA100', 'New York', 'London', 200, 150, 550.00);

INSERT INTO FLIGHTS (id, flight_number, origin, destination, capacity, available_seats, price) VALUES (2, 'BA200', 'London', 'Paris', 180, 120, 320.50);

INSERT INTO FLIGHTS (id, flight_number, origin, destination, capacity, available_seats, price) VALUES (3, 'LH300', 'Frankfurt', 'Rome', 220, 180, 410.75);

INSERT INTO FLIGHTS (id, flight_number, origin, destination, capacity, available_seats, price) VALUES (4, 'EK400', 'Dubai', 'Singapore', 350, 250, 780.25);

INSERT INTO FLIGHTS (id, flight_number, origin, destination, capacity, available_seats, price) VALUES (5, 'SQ500', 'Singapore', 'Sydney', 300, 200, 650.00);

ALTER TABLE FLIGHTS ALTER COLUMN id RESTART WITH 6;

-- Insert sample flight schedules
INSERT INTO FLIGHT_SCHEDULES (id, flight_id, departure_time, arrival_time, status) VALUES (1, 1, '2025-05-15T08:00:00', '2025-05-15T15:30:00', 'SCHEDULED');

INSERT INTO FLIGHT_SCHEDULES (id, flight_id, departure_time, arrival_time, status) VALUES (2, 1, '2025-05-16T09:00:00', '2025-05-16T16:30:00', 'SCHEDULED');

INSERT INTO FLIGHT_SCHEDULES (id, flight_id, departure_time, arrival_time, status) VALUES (3, 2, '2025-05-15T10:00:00', '2025-05-15T12:30:00', 'SCHEDULED');

INSERT INTO FLIGHT_SCHEDULES (id, flight_id, departure_time, arrival_time, status) VALUES (4, 2, '2025-05-16T11:00:00', '2025-05-16T13:30:00', 'SCHEDULED');

INSERT INTO FLIGHT_SCHEDULES (id, flight_id, departure_time, arrival_time, status) VALUES (5, 3, '2025-05-15T14:00:00', '2025-05-15T16:30:00', 'SCHEDULED');

INSERT INTO FLIGHT_SCHEDULES (id, flight_id, departure_time, arrival_time, status) VALUES (6, 4, '2025-05-16T23:00:00', '2025-05-17T11:30:00', 'SCHEDULED');

INSERT INTO FLIGHT_SCHEDULES (id, flight_id, departure_time, arrival_time, status) VALUES (7, 5, '2025-05-17T18:00:00', '2025-05-18T06:30:00', 'SCHEDULED');

ALTER TABLE FLIGHT_SCHEDULES ALTER COLUMN id RESTART WITH 6;