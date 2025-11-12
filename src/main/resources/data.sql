-- =========================================================
--  1️⃣ USERS - Basisgebruikers met rollen
-- =========================================================

-- ADMIN
INSERT INTO Users (id, name, email, password, role)
VALUES (1, 'Admin Gebruiker', 'admin@bhvtraining.nl',
        '$2a$12$BTnSof.xrBKtshV1x/sGdustDnjYqgeaXOHr4QYGsZS0basoWfcOW', 'ADMIN');

-- TRAINERS
INSERT INTO Users (id, name, email, password, role)
VALUES
    (2, 'Jan de Trainer', 'trainer1@bhvtraining.nl', '$2a$12$HmLc4.Q1cEo/RWwr4Ys2Ge2MvqbumCQ5QFhAxZexOruWUXsLqarr.', 'TRAINER'),
    (3, 'Sara Veilig', 'trainer2@bhvtraining.nl', '$2a$12$HmLc4.Q1cEo/RWwr4Ys2Ge2MvqbumCQ5QFhAxZexOruWUXsLqarr.', 'TRAINER'),
    (4, 'Tom Brand', 'trainer3@bhvtraining.nl', '$2a$12$HmLc4.Q1cEo/RWwr4Ys2Ge2MvqbumCQ5QFhAxZexOruWUXsLqarr.', 'TRAINER'),
    (5, 'Lisa Blusser', 'trainer4@bhvtraining.nl', '$2a$12$HmLc4.Q1cEo/RWwr4Ys2Ge2MvqbumCQ5QFhAxZexOruWUXsLqarr.', 'TRAINER'),
    (6, 'Mark Hulp', 'trainer5@bhvtraining.nl', '$2a$12$HmLc4.Q1cEo/RWwr4Ys2Ge2MvqbumCQ5QFhAxZexOruWUXsLqarr.', 'TRAINER'),
    (7, 'Anja Alarm', 'trainer6@bhvtraining.nl', '$2a$12$HmLc4.Q1cEo/RWwr4Ys2Ge2MvqbumCQ5QFhAxZexOruWUXsLqarr.', 'TRAINER'),
    (8, 'Peter Veilig', 'trainer7@bhvtraining.nl', '$2a$12$HmLc4.Q1cEo/RWwr4Ys2Ge2MvqbumCQ5QFhAxZexOruWUXsLqarr.', 'TRAINER'),
    (9, 'Kim Redder', 'trainer8@bhvtraining.nl', '$2a$12$HmLc4.Q1cEo/RWwr4Ys2Ge2MvqbumCQ5QFhAxZexOruWUXsLqarr.', 'TRAINER');

-- CURSISTEN
INSERT INTO users (id, name, email, password, role)
VALUES
    (10, 'Cursist 1', 'cursist1@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (11, 'Cursist 2', 'cursist2@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (12, 'Cursist 3', 'cursist3@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (13, 'Cursist 4', 'cursist4@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (14, 'Cursist 5', 'cursist5@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (15, 'Cursist 6', 'cursist6@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (16, 'Cursist 7', 'cursist7@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (17, 'Cursist 8', 'cursist8@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (18, 'Cursist 9', 'cursist9@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (19, 'Cursist 10', 'cursist10@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (20, 'Cursist 11', 'cursist11@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (21, 'Cursist 12', 'cursist12@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (22, 'Cursist 13', 'cursist13@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (23, 'Cursist 14', 'cursist14@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (24, 'Cursist 15', 'cursist15@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (25, 'Cursist 16', 'cursist16@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (26, 'Cursist 17', 'cursist17@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (27, 'Cursist 18', 'cursist18@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (28, 'Cursist 19', 'cursist19@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (29, 'Cursist 20', 'cursist20@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (30, 'Cursist 21', 'cursist21@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (31, 'Cursist 22', 'cursist22@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (32, 'Cursist 23', 'cursist23@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (33, 'Cursist 24', 'cursist24@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (34, 'Cursist 25', 'cursist25@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (35, 'Cursist 26', 'cursist26@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (36, 'Cursist 27', 'cursist27@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (37, 'Cursist 28', 'cursist28@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (38, 'Cursist 29', 'cursist29@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (39, 'Cursist 30', 'cursist30@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (40, 'Cursist 31', 'cursist31@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (41, 'Cursist 32', 'cursist32@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (42, 'Cursist 33', 'cursist33@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (43, 'Cursist 34', 'cursist34@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (44, 'Cursist 35', 'cursist35@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (45, 'Cursist 36', 'cursist36@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (46, 'Cursist 37', 'cursist37@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (47, 'Cursist 38', 'cursist38@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (48, 'Cursist 39', 'cursist39@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (49, 'Cursist 40', 'cursist40@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (50, 'Cursist 41', 'cursist41@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (51, 'Cursist 42', 'cursist42@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (52, 'Cursist 43', 'cursist43@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (53, 'Cursist 44', 'cursist44@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (54, 'Cursist 45', 'cursist45@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (55, 'Cursist 46', 'cursist46@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (56, 'Cursist 47', 'cursist47@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (57, 'Cursist 48', 'cursist48@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (58, 'Cursist 49', 'cursist49@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (59, 'Cursist 50', 'cursist50@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (60, 'Cursist 51', 'cursist51@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (61, 'Cursist 52', 'cursist52@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (62, 'Cursist 53', 'cursist53@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (63, 'Cursist 54', 'cursist54@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (64, 'Cursist 55', 'cursist55@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (65, 'Cursist 56', 'cursist56@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (66, 'Cursist 57', 'cursist57@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (67, 'Cursist 58', 'cursist58@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (68, 'Cursist 59', 'cursist59@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (69, 'Cursist 60', 'cursist60@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (70, 'Cursist 61', 'cursist61@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (71, 'Cursist 62', 'cursist62@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (72, 'Cursist 63', 'cursist63@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (73, 'Cursist 64', 'cursist64@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (74, 'Cursist 65', 'cursist65@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (75, 'Cursist 66', 'cursist66@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (76, 'Cursist 67', 'cursist67@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (77, 'Cursist 68', 'cursist68@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (78, 'Cursist 69', 'cursist69@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (79, 'Cursist 70', 'cursist70@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (80, 'Cursist 71', 'cursist71@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (81, 'Cursist 72', 'cursist72@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (82, 'Cursist 73', 'cursist73@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (83, 'Cursist 74', 'cursist74@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (84, 'Cursist 75', 'cursist75@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (85, 'Cursist 76', 'cursist76@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (86, 'Cursist 77', 'cursist77@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (87, 'Cursist 78', 'cursist78@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (88, 'Cursist 79', 'cursist79@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (89, 'Cursist 80', 'cursist80@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (90, 'Cursist 81', 'cursist81@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (91, 'Cursist 82', 'cursist82@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (92, 'Cursist 83', 'cursist83@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (93, 'Cursist 84', 'cursist84@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (94, 'Cursist 85', 'cursist85@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (95, 'Cursist 86', 'cursist86@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (96, 'Cursist 87', 'cursist87@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (97, 'Cursist 88', 'cursist88@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (98, 'Cursist 89', 'cursist89@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (99, 'Cursist 90', 'cursist90@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (100, 'Cursist 91', 'cursist91@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (101, 'Cursist 92', 'cursist92@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (102, 'Cursist 93', 'cursist93@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (103, 'Cursist 94', 'cursist94@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (104, 'Cursist 95', 'cursist95@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (105, 'Cursist 96', 'cursist96@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (106, 'Cursist 97', 'cursist97@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (107, 'Cursist 88', 'cursist98@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (108, 'Cursist 89', 'cursist99@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (109, 'Cursist 90', 'cursist100@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (110, 'Cursist 91', 'cursist101@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (111, 'Cursist 92', 'cursist102@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (112, 'Cursist 93', 'cursist103@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (113, 'Cursist 94', 'cursist104@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (114, 'Cursist 95', 'cursist105@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (115, 'Cursist 96', 'cursist106@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (116, 'Cursist 97', 'cursist107@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (117, 'Cursist 98', 'cursist108@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (118, 'Cursist 99', 'cursist109@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (119, 'Cursist 100', 'cursist120@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST');

-- =========================================================
--  2️⃣ TRAINER SUBCLASS
-- =========================================================
INSERT INTO trainer (id, expertise)
VALUES
    (2, 'BHV, EHBO & Ontruimingsoefeningen'),
    (3, 'BHV, EHBO & Ontruimingsoefeningen'),
    (4, 'BHV, EHBO & Ontruimingsoefeningen'),
    (5, 'BHV, EHBO & Ontruimingsoefeningen'),
    (6, 'BHV, EHBO & Ontruimingsoefeningen'),
    (7, 'BHV, EHBO & Ontruimingsoefeningen'),
    (8, 'BHV, EHBO & Ontruimingsoefeningen'),
    (9, 'BHV, EHBO & Ontruimingsoefeningen');

-- =========================================================
--  3️⃣ CURSIST SUBCLASS
-- =========================================================
INSERT INTO cursist (id, active)
VALUES
    (10, true),
    (11, true),
    (12, true),
    (13, true),
    (14, true),
    (15, true),
    (16, true),
    (17, true),
    (18, true),
    (19, true),
    (20, true),
    (21, true),
    (22, true),
    (23, true),
    (24, true),
    (25, true),
    (26, true),
    (27, true),
    (28, true),
    (29, true),
    (30, true),
    (31, true),
    (32, true),
    (33, true),
    (34, true),
    (35, true),
    (36, true),
    (37, true),
    (38, true),
    (39, true),
    (40, true),
    (41, true),
    (42, true),
    (43, true),
    (44, true),
    (45, true),
    (46, true),
    (47, true),
    (48, true),
    (49, true),
    (50, true),
    (51, true),
    (52, true),
    (53, true),
    (54, true),
    (55, true),
    (56, true),
    (57, true),
    (58, true),
    (59, true),
    (60, true),
    (61, true),
    (62, true),
    (63, true),
    (64, true),
    (65, true),
    (66, true),
    (67, true),
    (68, true),
    (69, true),
    (70, true),
    (71, true),
    (72, true),
    (73, true),
    (74, true),
    (75, true),
    (76, true),
    (77, true),
    (78, true),
    (79, true),
    (81, true),
    (82, true),
    (83, true),
    (84, true),
    (85, true),
    (86, true),
    (87, true),
    (88, true),
    (89, true),
    (90, true),
    (91, true),
    (92, true),
    (93, true),
    (94, true),
    (95, true),
    (96, true),
    (97, true),
    (98, true),
    (99, true),
    (100, true),
    (101, true),
    (102, true),
    (103, true),
    (104, true),
    (105, true),
    (106, true),
    (107, true),
    (108, true),
    (109, true),
    (110, true),
    (111, true),
    (112, true),
    (113, true),
    (114, true),
    (115, true),
    (116, true),
    (117, true),
    (118, true),
    (119, true);

-- =========================================================
--  4️⃣ LOCATIONS
-- =========================================================

INSERT INTO locations (id, company_name, address, city, postal_code, contact_person, email, phone_number,
                       lunch_provided, trainer_can_join_lunch, has_fire_extinguishing_facility, sufficient_classroom_space)
VALUES
    (1, 'BHV Centrum Amsterdam', 'Hoofdweg 12', 'Amsterdam', '1011AA', 'Piet Veilig', 'info@bhvcentrum.nl', '0612345678',
     true, true, true, true),

    (2, 'EHBO Rotterdam', 'Coolsingel 55', 'Rotterdam', '3011AA', 'Sara Zorg', 'info@ehborotterdam.nl', '0687654321',
     true, false, true, true),

    (3, 'BHV Centrum Utrecht', 'Vredenburg 10', 'Utrecht', '3511BA', 'Anja Blus', 'info@bhvutrecht.nl', '0611223344',
     true, true, true, true),

    (4, 'EHBO Den Haag', 'Spui 45', 'Den Haag', '2511BL', 'Kees Hulst', 'info@ehbodenhaag.nl', '0622334455',
     true, false, true, true),

    (5, 'BHV Breda Opleidingscentrum', 'Nieuwe Ginnekenstraat 100', 'Breda', '4811NR', 'Marijke Veilig', 'info@bhvbreda.nl', '0633445566',
     false, true, true, true),

    (6, 'EHBO Groningen', 'Oude Ebbingestraat 23', 'Groningen', '9712HA', 'Wim Eerstehulp', 'info@ehbogroningen.nl', '0644556677',
     true, false, true, true),

    (7, 'BHV Eindhoven', 'Kastelenplein 1', 'Eindhoven', '5653LX', 'Lars Alarm', 'info@bhveindhoven.nl', '0655667788',
     true, true, true, true),

    (8, 'EHBO Zwolle', 'Diezerstraat 33', 'Zwolle', '8011RH', 'Nina Zorg', 'info@ehbozwolle.nl', '0666778899',
     false, false, true, true),

    (9, 'BHV Nijmegen Trainingen', 'Oranjesingel 50', 'Nijmegen', '6511NW', 'Peter Veilig', 'info@bhvnijmegen.nl', '0677889900',
     true, true, true, true),

    (10, 'EHBO Tilburg Centrum', 'Heuvelring 25', 'Tilburg', '5038CP', 'Inge Eerstehulp', 'info@ehbotilburg.nl', '0688990011',
     true, false, true, true);

-- =========================================================
--  Cursussen per locatie (BHV/EHBO + Ontruimingsoefening)
-- =========================================================
INSERT INTO courses (
    id, name, description, start_date, end_date,
    max_participants, admin_override_allowed, type,
    trainer_id, location_id, report_required
)
VALUES
    -- 1. Amsterdam (Trainer 2)
    (1, 'BHV Basis Amsterdam',
     'Basistraining bedrijfshulpverlening met praktijkonderdelen.',
     '2025-10-20', '2025-10-21',
     10, false, 'BHV', 2, 1, false),

    (2, 'Ontruimingsoefening Fase 3 - Amsterdam',
     'Volledige oefening met vooraf aangekondigde ontruiming voor alle afdelingen.',
     '2025-12-02', '2025-12-02',
     NULL, true, 'ONTRUIMINGSOEFENING', 2, 1, true),

    -- 2. Rotterdam (Trainer 3)
    (3, 'EHBO Herhaling Rotterdam',
     'Opfristraining eerste hulp met AED gebruik.',
     '2025-11-05', '2025-11-05',
     10, false, 'EHBO', 2, 2, false),

    (4, 'Ontruimingsoefening Tabletop - Rotterdam',
     'Ontruimingsoefening op plattegrond.',
     '2025-11-17', '2025-11-17',
     NULL, false, 'ONTRUIMINGSOEFENING', 3, 2, true),

    -- 3. Utrecht (Trainer 2)
    (5, 'BHV Herhaling Utrecht',
     'Herhalingstraining BHV met nadruk op ontruimingsprocedures en brandbestrijding.',
     '2025-11-25', '2025-11-25',
     12, false, 'BHV', 3, 3, false),

    (6, 'Ontruimingsoefening Fase 3 - Utrecht',
     'Volledige oefening met vooraf aangekondigde ontruiming voor alle afdelingen.',
     '2025-12-02', '2025-12-02',
     NULL, true, 'ONTRUIMINGSOEFENING', 3, 3, true),

    -- 4. Den Haag (Trainer 2)
    (7, 'EHBO Basis Den Haag',
     'Basistraining eerste hulp en AED-bediening voor kantoorpersoneel.',
     '2025-10-30', '2025-10-30',
     10, false, 'EHBO', 4, 4, false),

    (8, 'Ontruimingsoefening Fase 4 - Den Haag',
     'Volledige onaangekondigde ontruiming gericht op realistische respons.',
     '2025-12-05', '2025-12-05',
     NULL, true, 'ONTRUIMINGSOEFENING', 4, 4, true),

    -- 5. Breda (Trainer 3)

    (9, 'BHV Basis Breda',
     'Praktische training voor beginnende bedrijfshulpverleners.',
     '2025-11-12', '2025-11-13',
     8, false, 'BHV', 5, 5, false),

    (10, 'Ontruimingsoefening Fase 5 - Breda',
     'Onaangekondigde oefening met figuranten als slachtoffers voor evaluatie.',
     '2025-12-08', '2025-12-08',
     NULL, true, 'ONTRUIMINGSOEFENING', 5, 5, true),

    -- 6. Groningen (Trainer 3)
    (11, 'EHBO Herhaling Groningen',
     'Herhalingstraining eerste hulp met scenario-oefeningen.',
     '2025-11-28', '2025-11-28',
     10, false, 'EHBO', 6, 6, false),

    (12, 'Ontruimingsoefening Fase 2 - Groningen',
     'Kleine ontruimingsoefening met focus op communicatie en evaluatie.',
     '2025-12-10', '2025-12-10',
     NULL, true, 'ONTRUIMINGSOEFENING', 5, 6, true),

    -- 7. Eindhoven (Trainer 3)
    (13, 'BHV Herhaling Eindhoven',
     'Opfristraining voor ervaren BHV’ers met nieuwe praktijkcasussen.',
     '2025-11-18', '2025-11-18',
     12, false, 'BHV', 7, 7, false),

    (14, 'Ontruimingsoefening Fase 3 - Eindhoven',
     'Aangekondigde oefening met alle medewerkers en BHV-team.',
     '2025-12-03', '2025-12-03',
     NULL, true, 'ONTRUIMINGSOEFENING', 7, 7, true),

    -- 8. Zwolle (Trainer 4)
    (15, 'EHBO Basis Zwolle',
     'Basistraining EHBO inclusief reanimatie en AED.',
     '2025-10-22', '2025-10-22',
     10, false, 'EHBO', 8, 8, false),

    (16, 'Ontruimingsoefening Fase 4 - Zwolle',
     'Realistische onaangekondigde oefening om reactiesnelheid te testen.',
     '2025-12-06', '2025-12-06',
     NULL, true, 'ONTRUIMINGSOEFENING', 8, 8, true),

    -- 9. Nijmegen (Trainer 4)
    (17, 'BHV Herhaling Nijmegen',
     'Herhalingstraining BHV met praktijkgerichte casussen.',
     '2025-11-15', '2025-11-15',
     10, false, 'BHV', 9, 9, false),

    (18, 'Ontruimingsoefening Fase 5 - Nijmegen',
     'Onaangekondigde oefening met figuranten voor realistische evaluatie.',
     '2025-12-12', '2025-12-12',
     NULL, true, 'ONTRUIMINGSOEFENING', 9, 9, true),

    -- 10. Tilburg (Trainer 4)
    (19, 'EHBO Herhaling Tilburg',
     'Herhalingstraining EHBO met focus op snelle besluitvorming.',
     '2025-11-20', '2025-11-20',
     10, false, 'EHBO', 2, 10, false),

    (20, 'Ontruimingsoefening Fase 2 - Tilburg',
     'Praktische ontruimingsoefening in meerdere afdelingen.',
     '2025-12-14', '2025-12-14',
     NULL, true, 'ONTRUIMINGSOEFENING', 2, 10, true);


-- =========================================================
--  Fase- en verslagverplichting correct instellen
-- =========================================================
UPDATE courses SET phase = 'TABLETOP', report_required = false WHERE id IN (4);
UPDATE courses SET phase = 'SMALL_SCENARIO', report_required = true WHERE id IN (2,12,20);
UPDATE courses SET phase = 'ANNOUNCED_EVACUATION', report_required = true WHERE id IN (6,14);
UPDATE courses SET phase = 'UNANNOUNCED_EVACUATION', report_required = true WHERE id IN (8,16);
UPDATE courses SET phase = 'UNANNOUNCED_WITH_VICTIMS', report_required = true WHERE id IN (10,18);


-- =========================================================
--  REGISTRATIONS - 100 testinschrijvingen (20 cursussen × 5 cursisten)
-- =========================================================
INSERT INTO registrations (id, registration_date, present, status, course_id, student_id)
VALUES
-- Cursus 1
(1, '2025-10-10', false, 'REGISTERED', 1, 10),
(2, '2025-10-10', false, 'REGISTERED', 1, 11),
(3, '2025-10-10', false, 'REGISTERED', 1, 12),
(4, '2025-10-10', false, 'REGISTERED', 1, 13),
(5, '2025-10-10', false, 'REGISTERED', 1, 14),
(6, '2025-10-11', false, 'REGISTERED', 1, 15),
(7, '2025-10-11', false, 'REGISTERED', 1, 16),
(8, '2025-10-11', false, 'REGISTERED', 1, 17),
(9, '2025-10-11', false, 'REGISTERED', 1, 18),
(10, '2025-10-11', false, 'REGISTERED', 1, 19),

-- Cursus 2
(11, '2025-10-10', false, 'REGISTERED', 2, 10),
(12, '2025-10-10', false, 'REGISTERED', 2, 11),
(13, '2025-10-10', false, 'REGISTERED', 2, 12),
(14, '2025-10-10', false, 'REGISTERED', 2, 13),
(15, '2025-10-10', false, 'REGISTERED', 2, 14),
(16, '2025-10-11', false, 'REGISTERED', 2, 15),
(17, '2025-10-11', false, 'REGISTERED', 2, 16),
(18, '2025-10-11', false, 'REGISTERED', 2, 17),
(19, '2025-10-11', false, 'REGISTERED', 2, 18),
(20, '2025-10-11', false, 'REGISTERED', 2, 19),

-- Cursus 3
(21, '2025-10-12', false, 'REGISTERED', 3, 20),
(22, '2025-10-12', false, 'REGISTERED', 3, 21),
(23, '2025-10-12', false, 'REGISTERED', 3, 22),
(24, '2025-10-12', false, 'REGISTERED', 3, 23),
(25, '2025-10-12', false, 'REGISTERED', 3, 24),
(26, '2025-10-13', false, 'REGISTERED', 3, 25),
(27, '2025-10-13', false, 'REGISTERED', 3, 26),
(28, '2025-10-13', false, 'REGISTERED', 3, 27),
(29, '2025-10-13', false, 'REGISTERED', 3, 28),
(30, '2025-10-13', false, 'REGISTERED', 3, 29),

-- Cursus 4
(31, '2025-10-12', false, 'REGISTERED', 4, 20),
(32, '2025-10-12', false, 'REGISTERED', 4, 21),
(33, '2025-10-12', false, 'REGISTERED', 4, 22),
(34, '2025-10-12', false, 'REGISTERED', 4, 23),
(35, '2025-10-12', false, 'REGISTERED', 4, 24),
(36, '2025-10-13', false, 'REGISTERED', 4, 25),
(37, '2025-10-13', false, 'REGISTERED', 4, 26),
(38, '2025-10-13', false, 'REGISTERED', 4, 27),
(39, '2025-10-13', false, 'REGISTERED', 4, 28),
(40, '2025-10-13', false, 'REGISTERED', 4, 29),

-- Cursus 5
(41, '2025-10-14', false, 'REGISTERED', 5, 30),
(42, '2025-10-14', false, 'REGISTERED', 5, 31),
(43, '2025-10-14', false, 'REGISTERED', 5, 32),
(44, '2025-10-14', false, 'REGISTERED', 5, 33),
(45, '2025-10-14', false, 'REGISTERED', 5, 34),
(46, '2025-10-15', false, 'REGISTERED', 5, 35),
(47, '2025-10-15', false, 'REGISTERED', 5, 36),
(48, '2025-10-15', false, 'REGISTERED', 5, 37),
(49, '2025-10-15', false, 'REGISTERED', 5, 38),
(50, '2025-10-15', false, 'REGISTERED', 5, 39),

-- Cursus 6
(51, '2025-10-14', false, 'REGISTERED', 6, 30),
(52, '2025-10-14', false, 'REGISTERED', 6, 31),
(53, '2025-10-14', false, 'REGISTERED', 6, 32),
(54, '2025-10-14', false, 'REGISTERED', 6, 33),
(55, '2025-10-14', false, 'REGISTERED', 6, 34),
(56, '2025-10-15', false, 'REGISTERED', 6, 35),
(57, '2025-10-15', false, 'REGISTERED', 6, 36),
(58, '2025-10-15', false, 'REGISTERED', 6, 37),
(59, '2025-10-15', false, 'REGISTERED', 6, 38),
(60, '2025-10-15', false, 'REGISTERED', 6, 39),

-- Cursus 7
(61, '2025-10-16', false, 'REGISTERED', 7, 40),
(62, '2025-10-16', false, 'REGISTERED', 7, 41),
(63, '2025-10-16', false, 'REGISTERED', 7, 42),
(64, '2025-10-16', false, 'REGISTERED', 7, 43),
(65, '2025-10-16', false, 'REGISTERED', 7, 44),
(66, '2025-10-17', false, 'REGISTERED', 7, 45),
(67, '2025-10-17', false, 'REGISTERED', 7, 46),
(68, '2025-10-17', false, 'REGISTERED', 7, 47),
(69, '2025-10-17', false, 'REGISTERED', 7, 48),
(70, '2025-10-17', false, 'REGISTERED', 7, 49),

-- Cursus 8
(71, '2025-10-16', false, 'REGISTERED', 8, 40),
(72, '2025-10-16', false, 'REGISTERED', 8, 41),
(73, '2025-10-16', false, 'REGISTERED', 8, 42),
(74, '2025-10-16', false, 'REGISTERED', 8, 43),
(75, '2025-10-16', false, 'REGISTERED', 8, 44),
(76, '2025-10-17', false, 'REGISTERED', 8, 45),
(77, '2025-10-17', false, 'REGISTERED', 8, 46),
(78, '2025-10-17', false, 'REGISTERED', 8, 47),
(79, '2025-10-17', false, 'REGISTERED', 8, 48),
(80, '2025-10-17', false, 'REGISTERED', 8, 49),

-- Cursus 9
(81, '2025-10-18', false, 'REGISTERED', 9, 50),
(82, '2025-10-18', false, 'REGISTERED', 9, 51),
(83, '2025-10-18', false, 'REGISTERED', 9, 52),
(84, '2025-10-18', false, 'REGISTERED', 9, 53),
(85, '2025-10-18', false, 'REGISTERED', 9, 54),
(86, '2025-10-19', false, 'REGISTERED', 9, 55),
(87, '2025-10-19', false, 'REGISTERED', 9, 56),
(88, '2025-10-19', false, 'REGISTERED', 9, 57),
(89, '2025-10-19', false, 'REGISTERED', 9, 58),
(90, '2025-10-19', false, 'REGISTERED', 9, 59),

-- Cursus 10
(91, '2025-10-18', false, 'REGISTERED', 10, 50),
(92, '2025-10-18', false, 'REGISTERED', 10, 51),
(93, '2025-10-18', false, 'REGISTERED', 10, 52),
(94, '2025-10-18', false, 'REGISTERED', 10, 53),
(95, '2025-10-18', false, 'REGISTERED', 10, 54),
(96, '2025-10-19', false, 'REGISTERED', 10, 55),
(97, '2025-10-19', false, 'REGISTERED', 10, 56),
(98, '2025-10-19', false, 'REGISTERED', 10, 57),
(99, '2025-10-19', false, 'REGISTERED', 10, 58),
(100, '2025-10-19', false, 'REGISTERED', 10, 59),

-- Cursus 11
(101, '2025-10-20', false, 'REGISTERED', 11, 60),
(102, '2025-10-20', false, 'REGISTERED', 11, 61),
(103, '2025-10-20', false, 'REGISTERED', 11, 62),
(104, '2025-10-20', false, 'REGISTERED', 11, 63),
(105, '2025-10-20', false, 'REGISTERED', 11, 64),
(106, '2025-10-21', false, 'REGISTERED', 11, 65),
(107, '2025-10-21', false, 'REGISTERED', 11, 66),
(108, '2025-10-21', false, 'REGISTERED', 11, 67),
(109, '2025-10-21', false, 'REGISTERED', 11, 68),
(110, '2025-10-21', false, 'REGISTERED', 11, 69),

-- Cursus 12
(111, '2025-10-20', false, 'REGISTERED', 12, 60),
(112, '2025-10-20', false, 'REGISTERED', 12, 61),
(113, '2025-10-20', false, 'REGISTERED', 12, 62),
(114, '2025-10-20', false, 'REGISTERED', 12, 63),
(115, '2025-10-20', false, 'REGISTERED', 12, 64),
(116, '2025-10-21', false, 'REGISTERED', 12, 65),
(117, '2025-10-21', false, 'REGISTERED', 12, 66),
(118, '2025-10-21', false, 'REGISTERED', 12, 67),
(119, '2025-10-21', false, 'REGISTERED', 12, 68),
(120, '2025-10-21', false, 'REGISTERED', 12, 69),

-- Cursus 13
(121, '2025-10-22', false, 'REGISTERED', 13, 70),
(122, '2025-10-22', false, 'REGISTERED', 13, 71),
(123, '2025-10-22', false, 'REGISTERED', 13, 72),
(124, '2025-10-22', false, 'REGISTERED', 13, 73),
(125, '2025-10-22', false, 'REGISTERED', 13, 74),
(126, '2025-10-23', false, 'REGISTERED', 13, 75),
(127, '2025-10-23', false, 'REGISTERED', 13, 76),
(128, '2025-10-23', false, 'REGISTERED', 13, 77),
(129, '2025-10-23', false, 'REGISTERED', 13, 78),
(130, '2025-10-23', false, 'REGISTERED', 13, 79),

-- Cursus 14
(131, '2025-10-22', false, 'REGISTERED', 14, 70),
(132, '2025-10-22', false, 'REGISTERED', 14, 71),
(133, '2025-10-22', false, 'REGISTERED', 14, 72),
(134, '2025-10-22', false, 'REGISTERED', 14, 73),
(135, '2025-10-22', false, 'REGISTERED', 14, 74),
(136, '2025-10-23', false, 'REGISTERED', 14, 75),
(137, '2025-10-23', false, 'REGISTERED', 14, 76),
(138, '2025-10-23', false, 'REGISTERED', 14, 77),
(139, '2025-10-23', false, 'REGISTERED', 14, 78),
(140, '2025-10-23', false, 'REGISTERED', 14, 79),

-- Cursus 15
(141, '2025-10-24', false, 'REGISTERED', 15, 80),
(142, '2025-10-24', false, 'REGISTERED', 15, 81),
(143, '2025-10-24', false, 'REGISTERED', 15, 82),
(144, '2025-10-24', false, 'REGISTERED', 15, 83),
(145, '2025-10-24', false, 'REGISTERED', 15, 84),
(146, '2025-10-25', false, 'REGISTERED', 15, 85),
(147, '2025-10-25', false, 'REGISTERED', 15, 86),
(148, '2025-10-25', false, 'REGISTERED', 15, 87),
(149, '2025-10-25', false, 'REGISTERED', 15, 88),
(150, '2025-10-25', false, 'REGISTERED', 15, 89),

-- Cursus 16
(151, '2025-10-24', false, 'REGISTERED', 16, 80),
(152, '2025-10-24', false, 'REGISTERED', 16, 81),
(153, '2025-10-24', false, 'REGISTERED', 16, 82),
(154, '2025-10-24', false, 'REGISTERED', 16, 83),
(155, '2025-10-24', false, 'REGISTERED', 16, 84),
(156, '2025-10-25', false, 'REGISTERED', 16, 85),
(157, '2025-10-25', false, 'REGISTERED', 16, 86),
(158, '2025-10-25', false, 'REGISTERED', 16, 87),
(159, '2025-10-25', false, 'REGISTERED', 16, 88),
(160, '2025-10-25', false, 'REGISTERED', 16, 89),

-- Cursus 17
(161, '2025-10-26', false, 'REGISTERED', 17, 90),
(162, '2025-10-26', false, 'REGISTERED', 17, 91),
(163, '2025-10-26', false, 'REGISTERED', 17, 92),
(164, '2025-10-26', false, 'REGISTERED', 17, 93),
(165, '2025-10-26', false, 'REGISTERED', 17, 94),
(166, '2025-10-27', false, 'REGISTERED', 17, 95),
(167, '2025-10-27', false, 'REGISTERED', 17, 96),
(168, '2025-10-27', false, 'REGISTERED', 17, 97),
(169, '2025-10-27', false, 'REGISTERED', 17, 98),
(170, '2025-10-27', false, 'REGISTERED', 17, 99),

-- Cursus 18
(171, '2025-10-26', false, 'REGISTERED', 18, 90),
(172, '2025-10-26', false, 'REGISTERED', 18, 91),
(173, '2025-10-26', false, 'REGISTERED', 18, 92),
(174, '2025-10-26', false, 'REGISTERED', 18, 93),
(175, '2025-10-26', false, 'REGISTERED', 18, 94),
(176, '2025-10-27', false, 'REGISTERED', 18, 95),
(177, '2025-10-27', false, 'REGISTERED', 18, 96),
(178, '2025-10-27', false, 'REGISTERED', 18, 97),
(179, '2025-10-27', false, 'REGISTERED', 18, 98),
(180, '2025-10-27', false, 'REGISTERED', 18, 99),

-- Cursus 19
(181, '2025-10-28', false, 'REGISTERED', 19, 100),
(182, '2025-10-28', false, 'REGISTERED', 19, 101),
(183, '2025-10-28', false, 'REGISTERED', 19, 102),
(184, '2025-10-28', false, 'REGISTERED', 19, 103),
(185, '2025-10-28', false, 'REGISTERED', 19, 104),
(186, '2025-10-29', false, 'REGISTERED', 19, 105),
(187, '2025-10-29', false, 'REGISTERED', 19, 106),
(188, '2025-10-29', false, 'REGISTERED', 19, 107),
(189, '2025-10-29', false, 'REGISTERED', 19, 108),
(190, '2025-10-29', false, 'REGISTERED', 19, 109),

-- Cursus 20
(191, '2025-10-28', false, 'REGISTERED', 20, 100),
(192, '2025-10-28', false, 'REGISTERED', 20, 101),
(193, '2025-10-28', false, 'REGISTERED', 20, 102),
(194, '2025-10-28', false, 'REGISTERED', 20, 103),
(195, '2025-10-28', false, 'REGISTERED', 20, 104),
(196, '2025-10-29', false, 'REGISTERED', 20, 105),
(197, '2025-10-29', false, 'REGISTERED', 20, 106),
(198, '2025-10-29', false, 'REGISTERED', 20, 107),
(199, '2025-10-29', false, 'REGISTERED', 20, 108),
(200, '2025-10-29', false, 'REGISTERED', 20, 109);


-- =========================================================
-- Sequence resets (zorg dat de volgende ID's kloppen)
-- =========================================================
SELECT setval('users_id_seq', (SELECT COALESCE(MAX(id), 1) FROM users));
SELECT setval('courses_id_seq', (SELECT COALESCE(MAX(id), 1) FROM courses));
SELECT setval('registrations_id_seq', (SELECT COALESCE(MAX(id), 1) FROM registrations));
SELECT setval('certificates_id_seq', (SELECT COALESCE(MAX(id), 1) FROM certificates));
SELECT setval('locations_id_seq', (SELECT COALESCE(MAX(id), 1) FROM locations));
SELECT setval('evacuation_reports_id_seq', (SELECT COALESCE(MAX(id), 1) FROM evacuation_reports));

