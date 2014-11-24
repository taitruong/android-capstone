package org.aliensource.symptommanagement.cloud;

import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.Doctor;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class TestUtils {

    public static final String PROTOCOL = "https";
    public static final String PORT = "8443";
    public static final String USER_PATIENT1 = "patient1";
    public static final String USER_DOCTOR1 = "doctor1";
    public static final String USER_DOCTOR1_PASS = "pass";

    private static final String[] POSSIBLE_LOCALHOSTS = {
            "192.168.1.5", // my laptop
            "10.0.2.2", // Android Emulator
            "192.168.56.1" // Genymotion
    };

    public static String findTheRealLocalhostAddress() {
/*
        String realLocalHost = null;

        for(String localhost : POSSIBLE_LOCALHOSTS) {
            try {
                URL url = new URL("https://"+localhost+":8443" + SecurityService.TOKEN_PATH);
                URLConnection con = url.openConnection();
                con.setConnectTimeout(500);
                con.setReadTimeout(500);
                InputStream in = con.getInputStream();
                if (in != null){
                    realLocalHost = localhost;
                    in.close();
                    break;
                }
            } catch (Exception e) {e.printStackTrace();}
        }

        return realLocalHost;
*/
        return "192.168.1.5";

    }

    public static Patient randomPatient() {
        Patient patient = randomPatientWithoutDoctorsAndRoles();
        patient.setRoles(randomRoles());
        return patient;
    }

    public static Patient randomPatientWithoutDoctorsAndRoles() {
        Patient patient = new Patient();
        patient.setFirstName(randomString());
        patient.setLastName(randomString());
        patient.setUsername(randomString());
        patient.setPassword(randomString());
        patient.setDateOfBirth(0);
        patient.setMedicalRecordNumber(randomString());

        return patient;
    }

    public static Doctor randomDoctor() {
        Doctor doctor = randomDoctorWithoutPatient();
        return doctor;
    }

    public static Doctor randomDoctorWithoutPatient() {
        Doctor doctor = new Doctor();
        doctor.setFirstName(randomString());
        doctor.setLastName(randomString());
        doctor.setUsername(randomString());
        doctor.setPassword(randomString());
        doctor.setRoles(randomRoles());
        doctor.setDateOfBirth(0);
        return doctor;
    }

    public static List<Role> randomRoles() {
        Role role = new Role();
        List<Role> roles = new ArrayList<Role>();
        roles.add(role);
        role.setName(randomString());
        return roles;
    }

    public static CheckIn randomCheckIn() {
        CheckIn checkIn = new CheckIn();

        return checkIn;
    }

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

}

