-- =========================================================
--  1️⃣ USERS - Basisgebruikers met rollen
-- =========================================================

-- ADMIN
INSERT INTO users (id, name, email, password, role)
VALUES (1, 'Admin Gebruiker', 'admin@bhvtraining.nl',
        '$2a$12$BTnSof.xrBKtshV1x/sGdustDnjYqgeaXOHr4QYGsZS0basoWfcOW', 'ADMIN');

-- TRAINERS
INSERT INTO users (id, name, email, password, role)
VALUES
    (2, 'Jan de Trainer', 'jan.trainer@bhvtraining.nl', '$2a$12$HmLc4.Q1cEo/RWwr4Ys2Ge2MvqbumCQ5QFhAxZexOruWUXsLqarr.', 'TRAINER'),
    (3, 'Sara Veilig', 'sara.veilig@bhvtraining.nl', '$2a$12$HmLc4.Q1cEo/RWwr4Ys2Ge2MvqbumCQ5QFhAxZexOruWUXsLqarr.', 'TRAINER'),
    (4, 'Tom Brand', 'tom.brand@bhvtraining.nl', '$2a$12$HmLc4.Q1cEo/RWwr4Ys2Ge2MvqbumCQ5QFhAxZexOruWUXsLqarr.', 'TRAINER');

-- CURSISTEN
INSERT INTO users (id, name, email, password, role)
VALUES
    (10, 'Cursist 1', 'cursist1@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (11, 'Cursist 2', 'cursist2@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (12, 'Cursist 3', 'cursist3@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (13, 'Cursist 4', 'cursist4@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST'),
    (14, 'Cursist 5', 'cursist5@bedrijf.nl', '$2a$12$JXXE5Thuao6G.7v.W..hbOe3vTBjXIneq5cjdsYUOOPbWomjn3tmK', 'CURSIST');

-- =========================================================
--  2️⃣ TRAINER SUBCLASS
-- =========================================================
INSERT INTO trainer (id, expertise)
VALUES
    (2, 'BHV, EHBO & Ontruimingsoefeningen'),
    (3, 'BHV, EHBO & Ontruimingsoefeningen'),
    (4, 'BHV, EHBO & Ontruimingsoefeningen');

-- =========================================================
--  3️⃣ CURSIST SUBCLASS
-- =========================================================
INSERT INTO cursist (id, active)
VALUES
    (10, true),
    (11, true),
    (12, true),
    (13, true),
    (14, true);

-- =========================================================
--  4️⃣ LOCATIONS
-- =========================================================
INSERT INTO locations (id, company_name, address, city, postal_code, contact_person, email, phone_number,
                       lunch_provided, trainer_can_join_lunch, has_fire_extinguishing_facility, sufficient_classroom_space)
VALUES
    (1, 'BHV Centrum Amsterdam', 'Hoofdweg 12', 'Amsterdam', '1011AA', 'Piet Veilig', 'info@bhvcentrum.nl', '0612345678',
     true, true, true, true),
    (2, 'EHBO Rotterdam', 'Coolsingel 55', 'Rotterdam', '3011AA', 'Sara Zorg', 'info@ehborotterdam.nl', '0687654321',
     true, false, true, true);

-- =========================================================
--  5️⃣ COURSES
-- =========================================================
INSERT INTO courses (id, name, description, start_date, end_date, max_participants, admin_override_allowed, type, trainer_id, location_id, report_required)
VALUES
    (1, 'BHV Basis Amsterdam', 'Basistraining bedrijfshulpverlening met praktijkonderdelen.', '2025-10-20', '2025-10-21', 10, true, 'BHV', 2, 1, true),
    (2, 'EHBO Herhaling Rotterdam', 'Opfristraining eerste hulp met AED gebruik.', '2025-11-05', '2025-11-05', 10, false, 'EHBO', 3, 2, true);

-- =========================================================
--  6️⃣ REGISTRATIONS
-- =========================================================
INSERT INTO registrations (id, registration_date, present, status, course_id, student_id)
VALUES
    (1, '2025-10-10', true, 'APPROVED', 1, 10),
    (2, '2025-10-10', true, 'APPROVED', 1, 11),
    (3, '2025-10-10', true, 'APPROVED', 1, 12),
    (4, '2025-10-11', true, 'APPROVED', 2, 13),
    (5, '2025-10-11', true, 'APPROVED', 2, 14);

-- =========================================================
--  7️⃣ CERTIFICATES (optioneel)
-- =========================================================
INSERT INTO certificates (id, certificate_number, issued_by, issue_date, expiry_date, course_id, student_id)
VALUES
    (1, 'CERT-2025-001', 'Jan de Trainer', '2025-10-21', '2026-10-21', 1, 10);
