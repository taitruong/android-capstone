/* Doctors */
INSERT INTO doctor VALUES (
    1, 0, 'John', 'Gastro', 'pass', 'doctor1'
);
INSERT INTO doctor VALUES (
    2, 0, 'Hans', 'Klempner', 'pass', 'doctor2'
);
INSERT INTO doctor VALUES (
    3, 0, 'Peter', 'Spritze', 'pass', 'doctor3'
);

/* Check-Ins */
INSERT INTO check_in VALUES (
    1
);
INSERT INTO check_in VALUES (
    2
);
INSERT INTO check_in VALUES (
    3
);


/* Patients */
INSERT INTO patient VALUES (
    1, 0, 'Heul', 'Suse', 'pass', 'patient1', 'privat-001', 1
);
INSERT INTO patient VALUES (
    2, 0, 'Mala', 'Ria', 'pass', 'patient2', 'AOK-0123', 2
);
INSERT INTO patient VALUES (
    3, 0, 'Weich', 'Ei', 'pass', 'patient3', 'privat-121344', 3
);

/* Relations between patients and doctors */
INSERT INTO patient_doctor VALUES (
    1, 1
);
INSERT INTO patient_doctor VALUES (
    2, 2
);
INSERT INTO patient_doctor VALUES (
    3, 3
);

/* Symptoms */
INSERT INTO symptom VALUES (
    1, 'SORE_THROAT'
);
INSERT INTO symptom VALUES (
    2, 'EAT_DRINK'
);

/* Medicaments */
INSERT INTO medicament VALUES (
    1, 'Lortab'
);
INSERT INTO medicament VALUES (
    2, 'OxyContin'
);

/* Medication */
INSERT INTO medication VALUES (
    1, 1 /* medication 1 for medicament 1 (Lortab) */
);
INSERT INTO medication VALUES (
    2, 2 /* medication 2 for medicament 2 (OxyContin) */
);
INSERT INTO medication VALUES (
    3, 1 /* medication 3 for medicament 1 (Lortab) */
);
INSERT INTO medication VALUES (
    4, 2 /* medication 4 for medicament 2 (OxyContin) */
);
INSERT INTO medication VALUES (
    5, 1 /* medication 5 for medicament 1 (Lortab) */
);
INSERT INTO medication VALUES (
    6, 2 /* medication 6 for medicament 2 (OxyContin) */
);

/* Join table mapping patients with their medications */
INSERT INTO patient_medications VALUES (
    1, 1 /* patient 1 with medication 1 */
);
INSERT INTO patient_medications VALUES (
    1, 2 /* patient 1 with medication 2 */
);
INSERT INTO patient_medications VALUES (
    2, 3 /* patient 2 with medication 3 */
);
INSERT INTO patient_medications VALUES (
    2, 4 /* patient 2 with medication 4 */
);
INSERT INTO patient_medications VALUES (
    3, 5 /* patient 3 with medication 5 */
);
INSERT INTO patient_medications VALUES (
    3, 6 /* patient 3 with medication 6 */
);

/* Roles for patients and doctors */
INSERT INTO role VALUES (
    1, 'ROLE_PATIENT'
);
INSERT INTO role VALUES (
    2, 'ROLE_DOCTOR'
);

/* Join table mapping patients and doctors with roles */
INSERT INTO patient_roles VALUES (
    1, 1 /* patient 1 with role 1 */
);
INSERT INTO patient_roles VALUES (
    2, 1 /* patient 2 with role 1 */
);
INSERT INTO patient_roles VALUES (
    3, 1 /* patient 3 with role 1 */
);

INSERT INTO doctor_roles VALUES (
    1, 2 /* doctor 1 with role 2 */
);
INSERT INTO doctor_roles VALUES (
    2, 2 /* doctor 2 with role 2 */
);
INSERT INTO doctor_roles VALUES (
    3, 2 /* doctor 3 with role 2 */
);
