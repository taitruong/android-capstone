/* Doctors */
INSERT INTO doctor VALUES (
    1, timestamp(1980-01-11), 'John', 'Gastro', 'pass', 'doctor1'
);
INSERT INTO doctor VALUES (
    2, timestamp(1981-02-12), 'Hans', 'Klempner', 'pass', 'doctor2'
);
INSERT INTO doctor VALUES (
    3, timestamp(1982-03-13), 'Peter', 'Spritze', 'pass', 'doctor3'
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
    1, timestamp(1990-10-11), 'Heul', 'Suse', 'pass', 'patient1', 'privat-001', 1
);
INSERT INTO patient VALUES (
    2, timestamp(1991-11-12), 'Mala', 'Ria', 'pass', 'patient2', 'AOK-0123', 2
);
INSERT INTO patient VALUES (
    3, timestamp(1992-12-13), 'Weich', 'Ei', 'pass', 'patient3', 'privat-121344', 3
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
