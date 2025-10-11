-- ============================================
-- USERS (Admin + Trainers + Cursisten)
-- ============================================public

-- ADMIN
INSERT INTO users (id, name, email, password, role, user_type)
VALUES (1, 'Admin Gebruiker', 'admin@bhvtraining.nl',
        '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C',
        'ADMIN', 'ADMIN');

-- TRAINERS
INSERT INTO users (id, name, email, password, role, user_type, expertise) VALUES
     (2, 'Jan de Trainer', 'jan.trainer@bhvtraining.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'TRAINER', 'TRAINER', 'BHV, EHBO & Ontruimingsoefeningen'),
     (3, 'Sara Veilig', 'sara.veilig@bhvtraining.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'TRAINER', 'TRAINER', 'BHV, EHBO & Ontruimingsoefeningen'),
     (4, 'Tom Brand', 'tom.brand@bhvtraining.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'TRAINER', 'TRAINER', 'BHV, EHBO & Ontruimingsoefeningen'),
     (5, 'Lisa Redder', 'lisa.redder@bhvtraining.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'TRAINER', 'TRAINER', 'BHV, EHBO & Ontruimingsoefeningen'),
     (6, 'Erik Blusser', 'erik.blusser@bhvtraining.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'TRAINER', 'TRAINER', 'BHV, EHBO & Ontruimingsoefeningen'),
     (7, 'Nina Nood', 'nina.nood@bhvtraining.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'TRAINER', 'TRAINER', 'BHV, EHBO & Ontruimingsoefeningen'),
     (8, 'Koen Kalm', 'koen.kalm@bhvtrainin.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'TRAINER', 'TRAINER', 'BHV, EHBO & Ontruimingsoefeningen'),
     (9, 'Anja Alarm', 'anja.alarm@bhvtraining.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'TRAINER', 'TRAINER', 'BHV, EHBO & Ontruimingsoefeningen'),
      (10, 'Mark Medic', 'mark.medic@bhvtraining.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'TRAINER', 'TRAINER', 'BHV, EHBO & Ontruimingsoefeningen'),
      (11, 'Rosa Redding', 'rosa.redding@bhvtraining.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'TRAINER', 'TRAINER', 'BHV, EHBO & Ontruimingsoefeningen');

-- CURSISTEN
INSERT INTO users (id, name, email, password, role, user_type, active) VALUES
    (12, 'Cursist 1', 'cursist1@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (13, 'Cursist 2', 'cursist2@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (14, 'Cursist 3', 'cursist3@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (15, 'Cursist 4', 'cursist4@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (16, 'Cursist 5', 'cursist5@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (17, 'Cursist 6', 'cursist6@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (18, 'Cursist 7', 'cursist7@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (19, 'Cursist 8', 'cursist8@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (20, 'Cursist 9', 'cursist9@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (21, 'Cursist 10', 'cursist10@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (22, 'Cursist 11', 'cursist11@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (23, 'Cursist 12', 'cursist12@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (24, 'Cursist 13', 'cursist13@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (25, 'Cursist 14', 'cursist14@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (26, 'Cursist 15', 'cursist15@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (27, 'Cursist 16', 'cursist16@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (28, 'Cursist 17', 'cursist17@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (29, 'Cursist 18', 'cursist18@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (30, 'Cursist 19', 'cursist19@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (31, 'Cursist 20', 'cursist20@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (32, 'Cursist 21', 'cursist21@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (33, 'Cursist 22', 'cursist22@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (34, 'Cursist 23', 'cursist23@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (35, 'Cursist 24', 'cursist24@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (36, 'Cursist 25', 'cursist25@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (37, 'Cursist 26', 'cursist26@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (38, 'Cursist 27', 'cursist27@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (39, 'Cursist 28', 'cursist28@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (40, 'Cursist 29', 'cursist29@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (41, 'Cursist 30', 'cursist30@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (42, 'Cursist 31', 'cursist31@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (43, 'Cursist 32', 'cursist32@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (44, 'Cursist 33', 'cursist33@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (45, 'Cursist 34', 'cursist34@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (46, 'Cursist 35', 'cursist35@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (47, 'Cursist 36', 'cursist36@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (48, 'Cursist 37', 'cursist37@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (49, 'Cursist 38', 'cursist38@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (50, 'Cursist 39', 'cursist39@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (51, 'Cursist 40', 'cursist40@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (52, 'Cursist 41', 'cursist41@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (53, 'Cursist 42', 'cursist42@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (54, 'Cursist 43', 'cursist43@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (55, 'Cursist 44', 'cursist44@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (56, 'Cursist 45', 'cursist45@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (57, 'Cursist 46', 'cursist46@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (58, 'Cursist 47', 'cursist47@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (59, 'Cursist 48', 'cursist48@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (60, 'Cursist 49', 'cursist49@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (61, 'Cursist 50', 'cursist50@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (62, 'Cursist 51', 'cursist51@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (64, 'Cursist 53', 'cursist53@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (65, 'Cursist 54', 'cursist54@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (66, 'Cursist 55', 'cursist55@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (67, 'Cursist 56', 'cursist56@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (68, 'Cursist 57', 'cursist57@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (69, 'Cursist 58', 'cursist58@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (70, 'Cursist 59', 'cursist59@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (71, 'Cursist 60', 'cursist60@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (72, 'Cursist 61', 'cursist61@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (73, 'Cursist 62', 'cursist62@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (74, 'Cursist 63', 'cursist63@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (75, 'Cursist 64', 'cursist64@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (76, 'Cursist 65', 'cursist65@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (77, 'Cursist 66', 'cursist66@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (78, 'Cursist 67', 'cursist67@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (79, 'Cursist 68', 'cursist68@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (80, 'Cursist 69', 'cursist69@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (81, 'Cursist 70', 'cursist70@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (82, 'Cursist 71', 'cursist71@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (83, 'Cursist 72', 'cursist72@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (84, 'Cursist 73', 'cursist73@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (85, 'Cursist 74', 'cursist74@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (86, 'Cursist 75', 'cursist75@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (87, 'Cursist 76', 'cursist76@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (88, 'Cursist 77', 'cursist77@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (89, 'Cursist 78', 'cursist78@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (90, 'Cursist 79', 'cursist79@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (91, 'Cursist 80', 'cursist80@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (92, 'Cursist 81', 'cursist81@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (93, 'Cursist 82', 'cursist82@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (94, 'Cursist 83', 'cursist83@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (95, 'Cursist 84', 'cursist84@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (96, 'Cursist 85', 'cursist85@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (97, 'Cursist 86', 'cursist86@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (98, 'Cursist 87', 'cursist87@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (99, 'Cursist 88', 'cursist88@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (100, 'Cursist 89', 'cursist89@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (101, 'Cursist 90', 'cursist90@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (102, 'Cursist 91', 'cursist91@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (103, 'Cursist 92', 'cursist92@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (104, 'Cursist 93', 'cursist93@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (105, 'Cursist 94', 'cursist94@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (106, 'Cursist 95', 'cursist95@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (107, 'Cursist 96', 'cursist96@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (108, 'Cursist 97', 'cursist97@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (109, 'Cursist 98', 'cursist98@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (110, 'Cursist 99', 'cursist99@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true),
    (111, 'Cursist 100', 'cursist100@bedrijf.nl', '{bcrypt}$2a$10$Kz6VhBo9ZB.Kg4nN/CJ5RO0bbArI1M8cE8Yq1nRzEw3GqN7pY4Y1C', 'CURSIST', 'CURSIST', true);

-- ============================================
-- LOCATIONS
-- ============================================

INSERT INTO locations (
    id, company_name, address, postal_code, city,
    contact_person, phone_number, email, notes,
    has_fire_extinguishing_facility, firefighting_area,
    sufficient_classroom_space, lunch_provided, trainer_can_join_lunch
) VALUES
      (1, 'Safety BV', 'Kerkstraat 25', '1017 GA', 'Amsterdam',
       'Jan Peters', '020-5551234', 'amsterdam@safetybv.nl',
       'Hoofdlocatie met grote oefenruimte.',
       true, 'Magazijn + kantoorruimte', true, true, true),

      (2, 'Veiligheidstraining Zuid', 'Havenweg 12', '3084 LD', 'Rotterdam',
       'Lisa de Vries', '010-4442233', 'rotterdam@veiligtraining.nl',
       'Trainingsruimte met buitenterrein voor blusoefeningen.',
       true, 'Oefenhal 1', true, false, true),

      (3, 'First Aid Center Utrecht', 'Vredenburg 9', '3511 BB', 'Utrecht',
       'Tom Bakker', '030-3345566', 'utrecht@firstaidcenter.nl',
       'Gelegen in stadscentrum, beperkte parkeergelegenheid.',
       true, 'Binnenruimte', true, true, false),

      (4, 'EHBO College Eindhoven', 'Lichtplein 7', '5611 XA', 'Eindhoven',
       'Emma Vos', '040-7896655', 'eindhoven@ehbocollege.nl',
       'Goede geluidsinstallatie, geschikt voor theorieonderdelen.',
       true, 'Leslokaal B', true, true, true),

      (5, 'Safety Campus Groningen', 'Eemskanaal 41', '9713 AJ', 'Groningen',
       'Sven Willems', '050-8823344', 'groningen@safetycampus.nl',
       'Grote hal voor simulatie-oefeningen.',
       true, 'Oefenruimte Noord', true, false, true),

      (6, 'BHV Centrum Zwolle', 'Stationsstraat 16', '8011 CW', 'Zwolle',
       'Kim Smits', '038-7771122', 'zwolle@bhvcentrum.nl',
       'Centrale ligging naast station.',
       false, 'Kantoorruimte', true, true, false),

      (7, 'Veiligheidsacademie Tilburg', 'Ringbaan Zuid 44', '5021 EC', 'Tilburg',
       'Noor van Dijk', '013-9932277', 'tilburg@veiligacademie.nl',
       'Inclusief oefenruimte voor brandbestrijding.',
       true, 'Hal 3', true, true, true),

      (8, 'Oefenlocatie Breda', 'Markendaalseweg 5', '4811 KC', 'Breda',
       'Ruben Jansen', '076-4446655', 'breda@bhvlo.nl',
       'Compacte ruimte voor kleine groepen.',
       false, 'Lesruimte A', true, false, false),

      (9, 'Safety Point Den Haag', 'Binckhorstlaan 101', '2516 BA', 'Den Haag',
       'Eva de Groot', '070-0000000', 'denhaag@safetypoint.nl',
       'Moderne trainingsfaciliteiten met kantine.',
       true, 'Oefenruimte C', true, true, true),

      (10, 'Rescue Training Nijmegen', 'Oranjesingel 30', '6511 NV', 'Nijmegen',
       'Daan Kuipers', '024-0000000', 'nijmegen@rescuetraining.nl',
       'Beschikt over buitenterrein voor praktijksessies.',
       true, 'Oefenterrein Buiten', true, true, true);


-- ============================================
-- COURSES
-- ============================================

INSERT INTO courses (
    id, name, description, start_date, end_date,
    max_participants, admin_override_allowed, type,
    trainer_id, location_id
) VALUES
-- BHV Basis Amsterdam – 11 deelnemers toegestaan via admin override
(1, 'BHV Basis Amsterdam',
 'Basistraining bedrijfshulpverlening met praktijkonderdelen.',
 '2025-10-20', '2025-10-21',
 10, true, 'BHV', 2, 1),

-- EHBO Herhaling Rotterdam
(2, 'EHBO Herhaling Rotterdam',
 'Opfristraining eerste hulp inclusief AED-gebruik.',
 '2025-10-23', '2025-10-23',
 8, false, 'EHBO', 3, 2),

-- Ontruimingsoefening Utrecht
(3, 'Ontruimingsoefening Utrecht',
 'Praktische ontruimingsoefening voor kantooromgeving.',
 '2025-10-25', '2025-10-25',
 0, false, 'ONTRUIMINGSOEFENING', 4, 3),

-- BHV Basis Eindhoven
(4, 'BHV Basis Eindhoven',
 'BHV-training gericht op productiebedrijven en magazijnen.',
 '2025-10-28', '2025-10-29',
 12, false, 'BHV', 5, 4),

-- EHBO Volledige Training Groningen
(5, 'EHBO Volledige Training Groningen',
 'Volledige EHBO-cursus inclusief reanimatie en verbandleer.',
 '2025-11-01', '2025-11-02',
 10, false, 'EHBO', 6, 5),

-- Ontruimingsoefening Zwolle
(6, 'Ontruimingsoefening Zwolle',
 'Simulatie van noodscenario’s voor BHV-teams.',
 '2025-11-05', '2025-11-05',
 0, false, 'ONTRUIMINGSOEFENING', 7, 6),

-- BHV Herhaling Tilburg
(7, 'BHV Herhaling Tilburg',
 'Herhalingstraining met nadruk op brandbestrijding en EHBO.',
 '2025-11-08', '2025-11-08',
 9, false, 'BHV', 8, 7),

-- EHBO Basis Breda
(8, 'EHBO Basis Breda',
 'Basiscursus EHBO met theorie en praktijkexamen.',
 '2025-11-12', '2025-11-13',
 10, false, 'EHBO', 9, 8),

-- Ontruimingsoefening Den Haag
(9, 'Ontruimingsoefening Den Haag',
 'Oefening gericht op communicatie tijdens noodsituaties.',
 '2025-11-15', '2025-11-15',
 0, false, 'ONTRUIMINGSOEFENING', 10, 9),

-- BHV en Ontruiming Nijmegen
(10, 'BHV en Ontruiming Nijmegen',
 'Combinatietraining BHV en ontruiming met certificering.',
 '2025-11-18', '2025-11-19',
 12, false, 'BHV', 11, 10);


-- ============================================
-- EVACUATION REPORTS
-- ============================================


INSERT INTO evacuation_reports (
    id, course_id, created_by_id, approved_by_id, phase,
    evacuation_time_minutes, building_size,
    observations, improvements, evaluation_advice,
    status, visible_for_students
) VALUES
      (1, 3, 4, 1, 'TABLETOP',
       15, 'Kantoorgebouw (3 verdiepingen)',
       'Cursisten volgden het evacuatieprotocol goed, lichte vertraging bij de nooduitgang.',
       'Verbeteren van routeaanduiding bij trappenhuis.',
       'Over het algemeen goed uitgevoerd, aanbevolen om jaarlijks te herhalen.',
       'APPROVED', true),

      (2, 6, 7, 1, 'UNANNOUNCED_EVACUATION',
       12, 'Magazijn en kantoorruimte',
       'Oefening onaangekondigd gestart, enkele medewerkers bleven op werkplek.',
       'Extra BHV-instructie over communicatie via portofoons.',
       'Aanbevolen om binnen zes maanden een vervolg te plannen.',
       'APPROVED', true),

      (3, 9, 10, 1, 'UNANNOUNCED_WITH_VICTIMS',
       20, 'Kantoorpand met 5 verdiepingen',
       'Verwarring bij het lokaliseren van slachtoffers, evacuatie duurde langer dan gepland.',
       'Meer nadruk leggen op rolverdeling en slachtofferopvang.',
       'Trainer stelt voor om volgende oefening met externe waarnemer te doen.',
       'PENDING', false);


-- ============================================
-- REGISTRATIONS
-- ============================================

-- ============================================
-- REGISTRATIONS
-- ============================================

INSERT INTO registrations (
    id, registration_date, present, status, course_id, student_id
) VALUES
-- Course 1: BHV Basis Amsterdam (max. 10, maar 11 toegestaan via override)
(1,  '2025-10-10', true,  'APPROVED', 1, 12),
(2,  '2025-10-10', true,  'APPROVED', 1, 13),
(3,  '2025-10-10', true,  'APPROVED', 1, 14),
(4,  '2025-10-10', true,  'APPROVED', 1, 15),
(5,  '2025-10-10', true,  'APPROVED', 1, 16),
(6,  '2025-10-10', true,  'APPROVED', 1, 17),
(7,  '2025-10-10', true,  'APPROVED', 1, 18),
(8,  '2025-10-10', true,  'APPROVED', 1, 19),
(9,  '2025-10-10', true,  'APPROVED', 1, 20),
(10, '2025-10-10', true,  'APPROVED', 1, 21),
(11, '2025-10-10', true,  'APPROVED', 1, 22), -- 11e deelnemer, toegestaan door adminOverride

-- Course 2: EHBO Herhaling Rotterdam (max. 8, krijgt 15 → voor test)
(12, '2025-10-11', true,  'APPROVED', 2, 23),
(13, '2025-10-11', true,  'APPROVED', 2, 24),
(14, '2025-10-11', true,  'APPROVED', 2, 25),
(15, '2025-10-11', true,  'APPROVED', 2, 26),
(16, '2025-10-11', true,  'APPROVED', 2, 27),
(17, '2025-10-11', true,  'APPROVED', 2, 28),
(18, '2025-10-11', true,  'APPROVED', 2, 29),
(19, '2025-10-11', true,  'APPROVED', 2, 30),
(20, '2025-10-11', true,  'APPROVED', 2, 31),
(21, '2025-10-11', true,  'APPROVED', 2, 32),
(22, '2025-10-11', true,  'APPROVED', 2, 33),
(23, '2025-10-11', true,  'APPROVED', 2, 34),
(24, '2025-10-11', true,  'APPROVED', 2, 35),
(25, '2025-10-11', true,  'APPROVED', 2, 36),
(26, '2025-10-11', true,  'APPROVED', 2, 37);
