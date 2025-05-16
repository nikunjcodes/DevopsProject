INSERT INTO TICKETS (id, user_id, flight_id, flight_schedule_id, passenger_name, seat_number, price, status, booking_time) VALUES (1, 1, 1, 1, 'John Doe', 'A12', 550.00, 'BOOKED', '2025-03-10T14:30:00');

INSERT INTO TICKETS (id, user_id, flight_id, flight_schedule_id, passenger_name, seat_number, price, status, booking_time) VALUES (2, 2, 1, 1, 'Jane Smith', 'B14', 550.00, 'BOOKED', '2025-03-10T15:45:00');

INSERT INTO TICKETS (id, user_id, flight_id, flight_schedule_id, passenger_name, seat_number, price, status, booking_time) VALUES (3, 3, 2, 3, 'Alice Johnson', 'C22', 320.50, 'BOOKED', '2025-03-11T09:15:00');

INSERT INTO TICKETS (id, user_id, flight_id, flight_schedule_id, passenger_name, seat_number, price, status, booking_time) VALUES (4, 4, 3, 5, 'Bob Brown', 'D05', 410.75, 'BOOKED', '2025-03-12T11:20:00');

-- Insert a cancelled ticket
INSERT INTO TICKETS (id, user_id, flight_id, flight_schedule_id, passenger_name, seat_number, price, status, booking_time) VALUES (5, 5, 4, 6, 'Charlie Davis', 'E18', 780.25, 'CANCELLED', '2025-03-12T16:40:00');

ALTER TABLE TICKETS ALTER COLUMN id RESTART WITH 6;