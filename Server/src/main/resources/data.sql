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
    (101, 70070400, 'Al', 'Capone', 'pass', 'patient101', 'medical-record-001'),
    (102, 335009100, 'Hugo', 'Sampson', 'pass', 'patient102', 'medical-record-002'),
    (103, 137747580, 'Marc', 'Meier', 'pass', 'patient103', 'medical-record-003'),
    (104, 377766780, 'Elisabeth', 'Sanders', 'pass', 'patient104', 'medical-record-004'),
    (105, -416594820, 'Tobias', 'Levine', 'pass', 'patient105', 'medical-record-005'),
    (106, -171737220, 'Sandra', 'Parker', 'pass', 'patient106', 'medical-record-006'),
    (107, -173317620, 'Simon', 'Star', 'pass', 'patient107', 'medical-record-007'),
    (108, 677376780, 'Christian', 'Bole', 'pass', 'patient108', 'medical-record-008'),
    (109, 253497600, 'Catherine', 'Mole', 'pass', 'patient109', 'medical-record-009'),
    (110, 4925400, 'Max', 'Jupiter', 'pass', 'patient110', 'medical-record-010'),
    (111, -115948200, 'Phil', 'Fellon', 'pass', 'patient111', 'medical-record-011'),
    (112, -166578600, 'Melanie', 'Jackson', 'pass', 'patient112', 'medical-record-012')
);

/* Relations between patients and doctors */
INSERT INTO patient_doctor VALUES (
    (1, 1),
    (101, 1),
    (102, 1),
    (103, 1),
    (104, 1),
    (105, 1),
    (106, 1),
    (107, 1),
    (108, 1),
    (109, 1),
    (110, 1),
    (111, 1),
    (112, 1),
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
  (  1, 1), /* medication 1 for medicament 1 (Lortab) */
  (  2, 2), /* medication 2 for medicament 2 (OxyContin) */
  (  3, 1), /* medication 3 for medicament 1 (Lortab) */
  (  4, 2), /* medication 4 for medicament 2 (OxyContin) */
  (  5, 1), /* medication 5 for medicament 1 (Lortab) */
  (  6, 2), /* medication 6 for medicament 2 (OxyContin) */
  (101, 1), /* medication 101 for medicament 1 (Lortab) */
  (102, 2), /* medication 102 for medicament 2 (OxyContin) */
  (103, 1), /* medication 103 for medicament 1 (Lortab) */
  (104, 2), /* medication 104 for medicament 2 (OxyContin) */
  (105, 1), /* medication 105 for medicament 1 (Lortab) */
  (106, 2), /* medication 106 for medicament 2 (OxyContin) */
  (107, 1), /* medication 107 for medicament 1 (Lortab) */
  (108, 2), /* medication 108 for medicament 2 (OxyContin) */
  (109, 1), /* medication 109 for medicament 1 (Lortab) */
  (110, 2), /* medication 110 for medicament 2 (OxyContin) */
  (111, 1), /* medication 111 for medicament 1 (Lortab) */
  (112, 2), /* medication 112 for medicament 2 (OxyContin) */
  (113, 1), /* medication 113 for medicament 1 (Lortab) */
  (114, 2), /* medication 114 for medicament 2 (OxyContin) */
  (115, 1), /* medication 115 for medicament 1 (Lortab) */
  (116, 2), /* medication 116 for medicament 2 (OxyContin) */
  (117, 1), /* medication 117 for medicament 1 (Lortab) */
  (118, 2), /* medication 118 for medicament 2 (OxyContin) */
  (119, 1), /* medication 119 for medicament 1 (Lortab) */
  (120, 2), /* medication 120 for medicament 2 (OxyContin) */
  (121, 1), /* medication 121 for medicament 1 (Lortab) */
  (122, 2), /* medication 122 for medicament 2 (OxyContin) */
  (123, 1), /* medication 123 for medicament 1 (Lortab) */
  (124, 2) /* medication 124 for medicament 2 (OxyContin) */
);

/* Join table mapping patients with their medications */
INSERT INTO patient_medications VALUES (
    (  1, 1), /* patient 1 with medication 1 */
    (  1, 2), /* patient 1 with medication 2 */
    (  2, 3), /* patient 2 with medication 3 */
    (  2, 4), /* patient 2 with medication 4 */
    (  3, 5), /* patient 3 with medication 5 */
    (  3, 6), /* patient 3 with medication 6 */
    (101, 101), /* patient 101 with medication 1 */
    (101, 102), /* patient 101 with medication 2 */
    (102, 103), /* patient 102 with medication 1 */
    (102, 104), /* patient 102 with medication 2 */
    (103, 105), /* patient 103 with medication 1 */
    (103, 106), /* patient 103 with medication 2 */
    (104, 107), /* patient 104 with medication 1 */
    (104, 108), /* patient 104 with medication 2 */
    (105, 109), /* patient 105 with medication 1 */
    (105, 110), /* patient 105 with medication 2 */
    (106, 111), /* patient 106 with medication 1 */
    (106, 112), /* patient 106 with medication 2 */
    (107, 113), /* patient 107 with medication 1 */
    (107, 114), /* patient 107 with medication 2 */
    (108, 115), /* patient 108 with medication 1 */
    (108, 116), /* patient 108 with medication 2 */
    (109, 117), /* patient 109 with medication 1 */
    (109, 118), /* patient 109 with medication 2 */
    (110, 119), /* patient 110 with medication 1 */
    (110, 120), /* patient 110 with medication 2 */
    (111, 121), /* patient 111 with medication 1 */
    (111, 122), /* patient 111 with medication 2 */
    (112, 123), /* patient 112 with medication 1 */
    (112, 124) /* patient 112 with medication 2 */
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
    (101, 1), /* patient 101 with role 1 */
    (102, 1), /* patient 102 with role 1 */
    (103, 1), /* patient 103 with role 1 */
    (104, 1), /* patient 104 with role 1 */
    (105, 1), /* patient 105 with role 1 */
    (106, 1), /* patient 106 with role 1 */
    (107, 1), /* patient 107 with role 1 */
    (108, 1), /* patient 108 with role 1 */
    (109, 1), /* patient 109 with role 1 */
    (110, 1), /* patient 110 with role 1 */
    (111, 1), /* patient 111 with role 1 */
    (112, 1) /* patient 112 with role 1 */
);

INSERT INTO doctor_roles VALUES (
    (1, 2), /* doctor 1 with role 2 */
    (2, 2), /* doctor 2 with role 2 */
    (3, 2) /* doctor 3 with role 2 */
);
