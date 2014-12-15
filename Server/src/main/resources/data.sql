/* Doctors */
INSERT INTO doctor VALUES (
    (1, 0, 'Derek', 'Shepherd', 'pass', 'doctor1'),
    (2, 0, 'Christina', 'Yang', 'pass', 'doctor2'),
    (3, 0, 'Miranda', 'Bailey', 'pass', 'doctor3')
);

/* Patients */
INSERT INTO patient VALUES (
    (1, 0, 'Jane', 'Doe', 'pass', 'patient1', 'JD1200987'),
    (2, 215395200, 'John', 'Doe', 'pass', 'patient2', 'AOK-0123'),
    (3, -20217600, 'Will', 'Smithers', 'pass', 'patient3', 'privat-121344'),
    (4, 70070400, 'Al', 'Capone', 'pass', 'patient4', 'medical-record-001'),
    (5, 335009100, 'Hugo', 'Sampson', 'pass', 'patient5', 'medical-record-002'),
    (6, 137747580, 'Marc', 'Meier', 'pass', 'patient6', 'medical-record-003'),
    (7, 377766780, 'Elisabeth', 'Sanders', 'pass', 'patient7', 'medical-record-004'),
    (8, -416594820, 'Tobias', 'Levine', 'pass', 'patient8', 'medical-record-005'),
    (9, -171737220, 'Sandra', 'Parker', 'pass', 'patient9', 'medical-record-006'),
    (10, -173317620, 'Simon', 'Star', 'pass', 'patient10', 'medical-record-007'),
    (11, 677376780, 'Christian', 'Bole', 'pass', 'patient11', 'medical-record-008'),
    (12, 253497600, 'Catherine', 'Mole', 'pass', 'patient12', 'medical-record-009'),
    (13, 4925400, 'Max', 'Jupiter', 'pass', 'patient13', 'medical-record-010'),
    (14, -115948200, 'Phil', 'Fellon', 'pass', 'patient14', 'medical-record-011'),
    (15, -166578600, 'Melanie', 'Jackson', 'pass', 'patient15', 'medical-record-012')
);

/* Relations between patients and doctors */
INSERT INTO patient_doctor VALUES (
    (1, 1),
    (4, 1),
    (5, 1),
    (6, 1),
    (7, 1),
    (8, 1),
    (9, 1),
    (10, 1),
    (11, 1),
    (12, 1),
    (13, 1),
    (14, 1),
    (15, 1),
    (2, 2),
    (3, 3)
);

/* Symptoms */
INSERT INTO symptom VALUES (
    (1, 'SORE_THROAT'),
    (2, 'EAT_DRINK')
);

/* Medicaments */
INSERT INTO medicament VALUES (
    (1, 'Lortab 10/500 mg'),
    (2, 'OxyContin 40 mg'),
    (3, 'MS CONTIN 30 mg'),
    (4, 'Vicodin 5/500 mg'),
    (5, 'Tofranil 50 mg'),
    (6, 'Lioresal 10 mg'),
    (7, 'Decadron 6 mg'),
    (8, 'Maxolon (Drops)'),
    (9, 'Alophen 5 mg')
);

/* Medication */
INSERT INTO medication VALUES (
  ( 1, 1, 1), /* medication 1 for medicament 1 (Lortab) */
  ( 2, 2, 1), /* medication 2 for medicament 2 (OxyContin) */
  ( 3, 1, 2), /* medication 3 for medicament 1 (Lortab) */
  ( 4, 2, 2), /* medication 4 for medicament 2 (OxyContin) */
  ( 5, 1, 3), /* medication 5 for medicament 1 (Lortab) */
  ( 6, 2, 3), /* medication 6 for medicament 2 (OxyContin) */
  ( 7, 1, 4), /* medication 7 for medicament 1 (Lortab) */
  ( 8, 2, 4), /* medication 8 for medicament 2 (OxyContin) */
  ( 9, 1, 5), /* medication 9 for medicament 1 (Lortab) */
  (10, 2, 5), /* medication 10 for medicament 2 (OxyContin) */
  (11, 1, 6), /* medication 11 for medicament 1 (Lortab) */
  (12, 2, 6), /* medication 12 for medicament 2 (OxyContin) */
  (13, 1, 7), /* medication 13 for medicament 1 (Lortab) */
  (14, 2, 7), /* medication 14 for medicament 2 (OxyContin) */
  (15, 1, 8), /* medication 15 for medicament 1 (Lortab) */
  (16, 2, 8), /* medication 16 for medicament 2 (OxyContin) */
  (17, 1, 9), /* medication 17 for medicament 1 (Lortab) */
  (18, 2, 9), /* medication 18 for medicament 2 (OxyContin) */
  (19, 1, 10), /* medication 19 for medicament 1 (Lortab) */
  (20, 2, 10), /* medication 20 for medicament 2 (OxyContin) */
  (21, 1, 11), /* medication 21 for medicament 1 (Lortab) */
  (22, 2, 11), /* medication 22 for medicament 2 (OxyContin) */
  (23, 1, 12), /* medication 23 for medicament 1 (Lortab) */
  (24, 2, 12), /* medication 24 for medicament 2 (OxyContin) */
  (25, 1, 13), /* medication 25 for medicament 1 (Lortab) */
  (26, 2, 13), /* medication 26 for medicament 2 (OxyContin) */
  (27, 1, 14), /* medication 27 for medicament 1 (Lortab) */
  (28, 2, 14), /* medication 28 for medicament 2 (OxyContin) */
  (29, 1, 15), /* medication 29 for medicament 1 (Lortab) */
  (30, 2, 15) /* medication 30 for medicament 2 (OxyContin) */
);

/* Roles for patients and doctors */
INSERT INTO role VALUES (
    (1, 'ROLE_PATIENT'),
    (2, 'ROLE_DOCTOR')
);

/* Join table mapping patients and doctors with roles */
INSERT INTO patient_roles VALUES (
    (  1, 1), /* patient 1 with role 1 */
    (  2, 1), /* patient 2 with role 1 */
    (  3, 1), /* patient 3 with role 1 */
    (4, 1), /* patient 4 with role 1 */
    (5, 1), /* patient 5 with role 1 */
    (6, 1), /* patient 6 with role 1 */
    (7, 1), /* patient 7 with role 1 */
    (8, 1), /* patient 8 with role 1 */
    (9, 1), /* patient 9 with role 1 */
    (10, 1), /* patient 10 with role 1 */
    (11, 1), /* patient 11 with role 1 */
    (12, 1), /* patient 12 with role 1 */
    (13, 1), /* patient 13 with role 1 */
    (14, 1), /* patient 14 with role 1 */
    (15, 1) /* patient 15 with role 1 */
);

INSERT INTO doctor_roles VALUES (
    (1, 2), /* doctor 1 with role 2 */
    (2, 2), /* doctor 2 with role 2 */
    (3, 2) /* doctor 3 with role 2 */
);
