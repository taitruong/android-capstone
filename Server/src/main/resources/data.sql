/* Doctors */
INSERT INTO doctor VALUES (
    1, 0, 'Derek', 'Shepherd', 'pass', 'doctor1'
);
INSERT INTO doctor VALUES (
    2, 0, 'Christina', 'Yang', 'pass', 'doctor2'
);
INSERT INTO doctor VALUES (
    3, 0, 'Miranda', 'Bailey', 'pass', 'doctor3'
);

/* Patients */
INSERT INTO patient VALUES (
    1, 0, 'Jane', 'Doe', 'pass', 'patient1', 'JD1200987'
);
INSERT INTO patient VALUES (
    2, 215395200, 'John', 'Doe', 'pass', 'patient2', 'AOK-0123'
);
INSERT INTO patient VALUES (
    3, -20217600, 'Will', 'Smithers', 'pass', 'patient3', 'privat-121344'
);
INSERT INTO patient VALUES (
    101, 70070400, 'Al', 'Capone', 'pass', 'patient101', 'medical-record-001'
);
INSERT INTO patient VALUES (
    102, 335009100, 'Hugo', 'Sampson', 'pass', 'patient102', 'medical-record-002'
);
INSERT INTO patient VALUES (
    103, 137747580, 'Marc', 'Meier', 'pass', 'patient103', 'medical-record-003'
);
INSERT INTO patient VALUES (
    104, 377766780, 'Elisabeth', 'Sanders', 'pass', 'patient104', 'medical-record-004'
);
INSERT INTO patient VALUES (
    105, -416594820, 'Tobias', 'Levine', 'pass', 'patient105', 'medical-record-005'
);
INSERT INTO patient VALUES (
    106, -171737220, 'Sandra', 'Parker', 'pass', 'patient106', 'medical-record-006'
);
INSERT INTO patient VALUES (
    107, -173317620, 'Simon', 'Star', 'pass', 'patient107', 'medical-record-007'
);
INSERT INTO patient VALUES (
    108, 677376780, 'Christian', 'Bole', 'pass', 'patient108', 'medical-record-008'
);
INSERT INTO patient VALUES (
    109, 253497600, 'Catherine', 'Mole', 'pass', 'patient109', 'medical-record-009'
);
INSERT INTO patient VALUES (
    110, 4925400, 'Max', 'Jupiter', 'pass', 'patient110', 'medical-record-010'
);
INSERT INTO patient VALUES (
    111, -115948200, 'Phil', 'Fellon', 'pass', 'patient111', 'medical-record-011'
);
INSERT INTO patient VALUES (
    112, -166578600, 'Melanie', 'Jackson', 'pass', 'patient112', 'medical-record-012'
);

/* Relations between patients and doctors */
INSERT INTO patient_doctor VALUES (
    1, 1
);
INSERT INTO patient_doctor VALUES (
    101, 1
);
INSERT INTO patient_doctor VALUES (
    102, 1
);
INSERT INTO patient_doctor VALUES (
    103, 1
);
INSERT INTO patient_doctor VALUES (
    104, 1
);
INSERT INTO patient_doctor VALUES (
    105, 1
);
INSERT INTO patient_doctor VALUES (
    106, 1
);
INSERT INTO patient_doctor VALUES (
    107, 1
);
INSERT INTO patient_doctor VALUES (
    108, 1
);
INSERT INTO patient_doctor VALUES (
    109, 1
);
INSERT INTO patient_doctor VALUES (
    110, 1
);
INSERT INTO patient_doctor VALUES (
    111, 1
);
INSERT INTO patient_doctor VALUES (
    112, 1
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
    1, 'Lortab 10/500 mg'
);
INSERT INTO medicament VALUES (
    2, 'OxyContin 40 mg'
);
INSERT INTO medicament VALUES (
    3, 'MS CONTIN 30 mg'
);
INSERT INTO medicament VALUES (
    4, 'Vicodin 5/500 mg'
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
INSERT INTO medication VALUES (
  101, 1 /* medication 1 for medicament 1 (Lortab) */
);
INSERT INTO medication VALUES (
  102, 2 /* medication 2 for medicament 2 (OxyContin) */
);
INSERT INTO medication VALUES (
  103, 1 /* medication 1 for medicament 1 (Lortab) */
);
INSERT INTO medication VALUES (
  104, 2 /* medication 2 for medicament 2 (OxyContin) */
);
INSERT INTO medication VALUES (
  105, 1 /* medication 1 for medicament 1 (Lortab) */
);
INSERT INTO medication VALUES (
  106, 2 /* medication 2 for medicament 2 (OxyContin) */
);
INSERT INTO medication VALUES (
  107, 1 /* medication 1 for medicament 1 (Lortab) */
);
INSERT INTO medication VALUES (
  108, 2 /* medication 2 for medicament 2 (OxyContin) */
);
INSERT INTO medication VALUES (
  109, 1 /* medication 1 for medicament 1 (Lortab) */
);
INSERT INTO medication VALUES (
  110, 2 /* medication 2 for medicament 2 (OxyContin) */
);
INSERT INTO medication VALUES (
  111, 1 /* medication 1 for medicament 1 (Lortab) */
);
INSERT INTO medication VALUES (
  112, 2 /* medication 2 for medicament 2 (OxyContin) */
);
INSERT INTO medication VALUES (
  113, 1 /* medication 1 for medicament 1 (Lortab) */
);
INSERT INTO medication VALUES (
  114, 2 /* medication 2 for medicament 2 (OxyContin) */
);
INSERT INTO medication VALUES (
  115, 1 /* medication 1 for medicament 1 (Lortab) */
);
INSERT INTO medication VALUES (
  116, 2 /* medication 2 for medicament 2 (OxyContin) */
);
INSERT INTO medication VALUES (
  117, 1 /* medication 1 for medicament 1 (Lortab) */
);
INSERT INTO medication VALUES (
  118, 2 /* medication 2 for medicament 2 (OxyContin) */
);
INSERT INTO medication VALUES (
  119, 1 /* medication 1 for medicament 1 (Lortab) */
);
INSERT INTO medication VALUES (
  120, 2 /* medication 2 for medicament 2 (OxyContin) */
);
INSERT INTO medication VALUES (
  121, 1 /* medication 1 for medicament 1 (Lortab) */
);
INSERT INTO medication VALUES (
  122, 2 /* medication 2 for medicament 2 (OxyContin) */
);
INSERT INTO medication VALUES (
  123, 1 /* medication 1 for medicament 1 (Lortab) */
);
INSERT INTO medication VALUES (
  124, 2 /* medication 2 for medicament 2 (OxyContin) */
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
INSERT INTO patient_medications VALUES (
    101, 101 /* patient 1 with medication 1 */
);
INSERT INTO patient_medications VALUES (
    101, 102 /* patient 1 with medication 2 */
);
INSERT INTO patient_medications VALUES (
    102, 103 /* patient 1 with medication 1 */
);
INSERT INTO patient_medications VALUES (
    102, 104 /* patient 1 with medication 2 */
);
INSERT INTO patient_medications VALUES (
    103, 105 /* patient 1 with medication 1 */
);
INSERT INTO patient_medications VALUES (
    103, 106 /* patient 1 with medication 2 */
);
INSERT INTO patient_medications VALUES (
    104, 107 /* patient 1 with medication 1 */
);
INSERT INTO patient_medications VALUES (
    104, 108 /* patient 1 with medication 2 */
);
INSERT INTO patient_medications VALUES (
    105, 109 /* patient 1 with medication 1 */
);
INSERT INTO patient_medications VALUES (
    105, 110 /* patient 1 with medication 2 */
);
INSERT INTO patient_medications VALUES (
    106, 111 /* patient 1 with medication 1 */
);
INSERT INTO patient_medications VALUES (
    106, 112 /* patient 1 with medication 2 */
);
INSERT INTO patient_medications VALUES (
    107, 113 /* patient 1 with medication 1 */
);
INSERT INTO patient_medications VALUES (
    107, 114 /* patient 1 with medication 2 */
);
INSERT INTO patient_medications VALUES (
    108, 115 /* patient 1 with medication 1 */
);
INSERT INTO patient_medications VALUES (
    108, 116 /* patient 1 with medication 2 */
);
INSERT INTO patient_medications VALUES (
    109, 117 /* patient 1 with medication 1 */
);
INSERT INTO patient_medications VALUES (
    109, 118 /* patient 1 with medication 2 */
);
INSERT INTO patient_medications VALUES (
    110, 119 /* patient 1 with medication 1 */
);
INSERT INTO patient_medications VALUES (
    110, 120 /* patient 1 with medication 2 */
);
INSERT INTO patient_medications VALUES (
    111, 121 /* patient 1 with medication 1 */
);
INSERT INTO patient_medications VALUES (
    111, 122 /* patient 1 with medication 2 */
);
INSERT INTO patient_medications VALUES (
    112, 123 /* patient 1 with medication 1 */
);
INSERT INTO patient_medications VALUES (
    112, 124 /* patient 1 with medication 2 */
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
INSERT INTO patient_roles VALUES (
    101, 1 /* patient 3 with role 1 */
);
INSERT INTO patient_roles VALUES (
    102, 1 /* patient 3 with role 1 */
);
INSERT INTO patient_roles VALUES (
    103, 1 /* patient 3 with role 1 */
);
INSERT INTO patient_roles VALUES (
    104, 1 /* patient 3 with role 1 */
);
INSERT INTO patient_roles VALUES (
    105, 1 /* patient 3 with role 1 */
);
INSERT INTO patient_roles VALUES (
    106, 1 /* patient 3 with role 1 */
);
INSERT INTO patient_roles VALUES (
    107, 1 /* patient 3 with role 1 */
);
INSERT INTO patient_roles VALUES (
    108, 1 /* patient 3 with role 1 */
);
INSERT INTO patient_roles VALUES (
    109, 1 /* patient 3 with role 1 */
);
INSERT INTO patient_roles VALUES (
    110, 1 /* patient 3 with role 1 */
);
INSERT INTO patient_roles VALUES (
    111, 1 /* patient 3 with role 1 */
);
INSERT INTO patient_roles VALUES (
    112, 1 /* patient 3 with role 1 */
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
