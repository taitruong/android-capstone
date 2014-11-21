package org.aliensource.symptommanagement.cloud;

import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.Doctor;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.UUID;

public class TestUtils {

    public static final String PROTOCOL = "https";
    public static final String PORT = "8443";
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
                System.out.println(">>>>> " + localhost);
                URL url = new URL("https://"+localhost+":8443" + VideoSvcApi.TOKEN_PATH);
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
        Patient patient = randomPatientWithoutDoctors();
        return patient;
    }

    public static Patient randomPatientWithoutDoctors() {
        Patient patient = new Patient();
        patient.setFirstName(randomString());
        patient.setLastName(randomString());
        patient.setUsername(randomString());
        patient.setPassword(randomString());
        patient.setRoles(randomRoles());
        patient.setDateOfBirth(new GregorianCalendar());
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
        doctor.setDateOfBirth(new GregorianCalendar());
        return doctor;
    }

    public static Collection<Role> randomRoles() {
        Role role = new Role();
        Collection<Role> roles = new ArrayList<Role>();
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

