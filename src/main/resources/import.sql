-- Створення користувачів
INSERT INTO users (firstname, lastname, username, password, isactive) VALUES ('Humeniuk', 'Dmytro', 'Dmytro.Humeniuk', 'pass123', true), ('Ivanov', 'Oleg', 'Oleg.Ivanov', 'pass456', true), ('Petrov', 'Serhii', 'Serhii.Petrov', 'pass789', true), ('Kovalenko', 'Olena', 'Olena.Kovalenko', 'pass101', true), ('Bohdan', 'Sydorenko', 'Bohdan.Sydorenko', 'pass112', true);

-- Створення типів тренувань
INSERT INTO trainingtype (trainingtype) VALUES ('FITNESS'), ('YOGA'), ('MARTIAL ARTS'), ('RUNNING'), ('SWIMMING');

-- Додавання підопічних
INSERT INTO trainee(address, date_of_birth, user_id) VALUES ('Mars Street', '1990-05-12', 1), ('Earth Street', '1995-02-25', 2), ('Sun Street', '1992-07-19', 3), ('Moon Street', '1988-11-30', 4), ('Galaxy Street', '1993-09-15', 5);

-- Додавання тренерів
INSERT INTO trainer(specialization, user_id) VALUES (1, 1), (2, 2), (3, 3), (4, 4), (5, 5);

-- Додавання тренувань
INSERT INTO training(duration, trainingdate, trainingname, trainee_id, trainer_id, training_type_id) VALUES (60, '2025-03-25', 'Get Your Body Fit', 1, 1, 1), (45, '2025-03-26', 'Yoga for Beginners', 2, 2, 2), (30, '2025-03-27', 'Martial Arts Training', 3, 3, 3), (40, '2025-03-28', 'Morning Running', 4, 4, 4), (50, '2025-03-29', 'Swimming for Health', 5, 5, 5);
