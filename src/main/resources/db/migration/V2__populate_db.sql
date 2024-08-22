INSERT INTO client (name) VALUES
('Alice'),
('Bob'),
('Charlie'),
('David'),
('Eva'),
('Frank'),
('Grace'),
('Hank'),
('Ivy'),
('John');

INSERT INTO planet (id, name) VALUES
('MARS', 'Mars'),
('VEN', 'Venus'),
('EARTH', 'Earth'),
('JUP', 'Jupiter'),
('SAT', 'Saturn');

INSERT INTO ticket (created_at, client_id, from_planet_id, to_planet_id) VALUES
(CURRENT_TIMESTAMP, 1, 'EARTH', 'MARS'),
(CURRENT_TIMESTAMP, 2, 'VEN', 'EARTH'),
(CURRENT_TIMESTAMP, 3, 'MARS', 'JUP'),
(CURRENT_TIMESTAMP, 4, 'JUP', 'SAT'),
(CURRENT_TIMESTAMP, 5, 'SAT', 'EARTH'),
(CURRENT_TIMESTAMP, 6, 'EARTH', 'VEN'),
(CURRENT_TIMESTAMP, 7, 'VEN', 'MARS'),
(CURRENT_TIMESTAMP, 8, 'MARS', 'SAT'),
(CURRENT_TIMESTAMP, 9, 'JUP', 'EARTH'),
(CURRENT_TIMESTAMP, 10, 'SAT', 'VEN');
