package org.aliensource.symptommanagement.cloud.integration.test;

import com.google.gson.JsonObject;

import org.aliensource.symptommanagement.client.oauth.SecuredRestException;
import org.aliensource.symptommanagement.cloud.TestUtils;
import org.aliensource.symptommanagement.cloud.repository.Alarm;
import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.IntakeTime;
import org.aliensource.symptommanagement.cloud.repository.Medicament;
import org.aliensource.symptommanagement.cloud.repository.Medication;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.Symptom;
import org.aliensource.symptommanagement.cloud.repository.SymptomTime;
import org.aliensource.symptommanagement.cloud.service.AlarmSvcApi;
import org.aliensource.symptommanagement.cloud.service.CheckInSvcApi;
import org.aliensource.symptommanagement.cloud.service.MedicationSvcApi;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.aliensource.symptommanagement.cloud.service.SymptomSvcApi;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit.RetrofitError;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PatientSvcApiIntegrationTest extends BaseSvcApiIntegrationTest<PatientSvcApi> {

    private final Patient model = TestUtils.randomPatientWithoutDoctorsAndRoles();

    @Test
    public void testAdd() throws Exception {
        // Add the video
        Patient newModel = service.save(model);

        List<Patient> data = service.findAll();
        assertNotNull(data);
        assertTrue(newModel + ": not in " + data, data.contains(newModel));
    }

    @Test
    public void testAccessDeniedWithIncorrectCredentials() throws Exception {

        try {
            // Add the model
            invalidClientService.save(model);

            fail("The server should have prevented the client from adding a model"
                    + " because it presented invalid client/user credentials");
        } catch (RetrofitError e) {
            assert (e.getCause() instanceof SecuredRestException);
        }
    }

    @Test
    public void testReadOnlyClientAccess() throws Exception {

        List<Patient> data = readOnlyService.findAll();
        assertNotNull(data);

        try {
            readOnlyService.save(model);

            fail("The server should have prevented the client from adding a model"
                    + " because it is using a read-only client ID");
        } catch (RetrofitError e) {
            JsonObject body = (JsonObject) e.getBodyAs(JsonObject.class);
            assertEquals("insufficient_scope", body.get("error").getAsString());
        }
    }

    @Test
    public void testFindByUsername() {
        Patient model = readOnlyService.findByUsername("patient1");
        assertNotNull(model);
        assertEquals("patient1", model.getUsername());
    }

    @Test
    public void testFindByDoctorUsernameAndFilter() {
        List<Patient> data = readOnlyService.findByDoctorUsernameAndFilter("doctor1", "");
        assertNotNull(data);
        assertTrue(data.size() > 0);
        data = readOnlyService.findByDoctorUsernameAndFilter("doctor1", "Jane");
        assertNotNull(data);
        assertTrue(data.size() > 0);
        data = readOnlyService.findByDoctorUsernameAndFilter("doctor1", "Doe");
        assertNotNull(data);
        assertTrue(data.size() > 0);
        data = readOnlyService.findByDoctorUsernameAndFilter("doctor1", "JD1200987");
        assertNotNull(data);
        assertTrue(data.size() > 0);
        data = readOnlyService.findByDoctorUsernameAndFilter("doctor1", "1200987");
        assertNotNull(data);
        assertTrue(data.size() > 0);
        data = readOnlyService.findByDoctorUsernameAndFilter("doctor1", "XXXXXXXXXXXXXXXXX");
        assertNotNull(data);
        assertEquals(0, data.size());
    }

    @Test
    public void testFindAll() {
        List<Patient> models = readOnlyService.findAll();
        assertNotNull(models);
        assertTrue(models.size() > 0);
    }

    @Test
    public void testCheckIn() {
        //the patient we want to do the check-in
        long patientId = 1;
        Patient patient = service.findOne(patientId);
        assertNotNull(patient);

        //create the symptom times
        List<SymptomTime> symptomTimes = new ArrayList<SymptomTime>();
        SymptomSvcApi symptomSvcApi = getService(SymptomSvcApi.class);
        for (String type : new String[]{ServiceUtils.SYMPTOM_TYPE_SORE_THROAT, ServiceUtils.SYMPTOM_TYPE_EAT_DRINK}) {
            SymptomTime symptomTime = new SymptomTime();
            //set symptom
            List<Symptom> symptoms = symptomSvcApi.findByType(type);
            assertNotNull(symptoms);
            assertTrue(symptoms.size() == 1);
            symptomTime.setSymptom(symptoms.get(0));

            symptomTime.setSeverity(1);
            symptomTime.setTimestamp(new GregorianCalendar().getTimeInMillis());

            symptomTimes.add(symptomTime);
        }

        //create the intake times for the medications
        List<IntakeTime> intakeTimes = new ArrayList<IntakeTime>();
        for (Medication medication : patient.getMedications()) {
            IntakeTime intakeTime = new IntakeTime();
            intakeTime.setMedicament(medication.getMedicament());
            intakeTime.setTimestamp(new GregorianCalendar().getTimeInMillis());
            intakeTimes.add(intakeTime);
        }

        //attach to check-in
        CheckIn checkIn = new CheckIn();
        checkIn.setTimestamp(new GregorianCalendar().getTimeInMillis());
        checkIn.setIntakeTimes(intakeTimes);
        checkIn.setSymptomTimes(symptomTimes);

        int beforeCheckInSize = patient.getCheckIns().size();

        patient = service.addCheckIn(patientId, checkIn);

        //check-in saved?
        assertEquals(beforeCheckInSize + 1, patient.getCheckIns().size());
        //get last check-in
        checkIn = patient.getCheckIns().get(beforeCheckInSize);
        //symptom times saved?
        assertEquals(symptomTimes.size(), checkIn.getSymptomTimes().size());
        //intake times saved?
        assertEquals(intakeTimes.size(), checkIn.getIntakeTimes().size());
        //test call to check-in controller to make sure whether there is no JPA or JSON problem
        CheckInSvcApi checkInSvcApi = getService(CheckInSvcApi.class);
        checkInSvcApi.findAll();
    }

    @Test
    public void testAddDeleteMedication() {
        Patient patient = service.save(TestUtils.randomPatientWithoutDoctorsAndRoles());
        assertNotNull(patient);
        patient = service.addMedicamentForPatient(patient.getId(), 1);
        assertNotNull(patient);
        assertEquals(1, patient.getMedications().size());
        //check reference to medication exists
        assertNotNull(patient.getMedications().get(0).getMedicament());
        assertEquals(1, patient.getMedications().get(0).getMedicament().getId());

        //remove medication from patient
        Medication medication = patient.getMedications().get(0);
        Medicament medicament = medication.getMedicament();
        //remove medication from patient
        patient = service.deleteMedicationForPatient(patient.getId(), medication.getId());
        assertNotNull(patient);
        assertNotNull(patient.getMedications());
        boolean found = false;
        for (Medication m : patient.getMedications()) {
            if (m.getMedicament().getId() == medicament.getId()) {
                found = true;
                break;
            }
        }
        assertFalse(found);
    }

    @Test
    public void testSoreThroatAlarm() {
        //the patient we want to do the check-in
        long patientId = 1;
        Patient patient = service.findOne(patientId);
        assertNotNull(patient);

        //create the one symptom time for Sore Throat
        Calendar timestamp = new GregorianCalendar();
        //go 5 days back
        timestamp.add(Calendar.DAY_OF_MONTH, -5);
        CheckIn checkIn = createCheckInWithSoreThroatSymptom(timestamp.getTimeInMillis(), 1);

        AlarmSvcApi alarmSvcApi = getService(AlarmSvcApi.class);
        List<Alarm> firstAlarms = alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(firstAlarms);
        int alarmFirstSize = firstAlarms.size();
        patient = service.addCheckIn(patientId, checkIn);
        List<Alarm> secondAlarms = alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        int alarmSecondSize = secondAlarms.size();

        //no alarm should be created yet
        assertEquals(alarmFirstSize, alarmSecondSize);

        timestamp.add(Calendar.HOUR_OF_DAY, 12);
        checkIn = createCheckInWithSoreThroatSymptom(timestamp.getTimeInMillis(), 0);
        patient = service.addCheckIn(patientId, checkIn);
        secondAlarms = alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        alarmSecondSize = secondAlarms.size();
        //no alarm should be created yet
        assertEquals(alarmFirstSize, alarmSecondSize);

        //test severe alarm
        //first severe checkin
        timestamp.add(Calendar.HOUR_OF_DAY, 3);
        checkIn = createCheckInWithSoreThroatSymptom(timestamp.getTimeInMillis(), 2);
        patient = service.addCheckIn(patientId, checkIn);
        secondAlarms = alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        alarmSecondSize = secondAlarms.size();
        //no alarm should be created yet
        assertEquals(alarmFirstSize, alarmSecondSize);
        //second severe alarm
        timestamp.add(Calendar.HOUR_OF_DAY, 12);
        checkIn = createCheckInWithSoreThroatSymptom(timestamp.getTimeInMillis(), 2);
        patient = service.addCheckIn(patientId, checkIn);
        secondAlarms = alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        alarmSecondSize = secondAlarms.size();
        //alarm should be created
        assertEquals(alarmFirstSize + 1, alarmSecondSize);

        //third severe alarm
        timestamp.add(Calendar.HOUR_OF_DAY, 20);
        checkIn = createCheckInWithSoreThroatSymptom(timestamp.getTimeInMillis(), 2);
        patient = service.addCheckIn(patientId, checkIn);
        secondAlarms = alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        alarmSecondSize = secondAlarms.size();
        //alarm should be created
        assertEquals(alarmFirstSize + 2, alarmSecondSize);

        //fourth moderate alarm
        timestamp.add(Calendar.HOUR_OF_DAY, 16);
        checkIn = createCheckInWithSoreThroatSymptom(timestamp.getTimeInMillis(), 1);
        patient = service.addCheckIn(patientId, checkIn);
        secondAlarms = alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        alarmSecondSize = secondAlarms.size();
        //alarm should be created
        assertEquals(alarmFirstSize + 3, alarmSecondSize);

        //fifth moderate alarm
        timestamp.add(Calendar.HOUR_OF_DAY, 11);
        checkIn = createCheckInWithSoreThroatSymptom(timestamp.getTimeInMillis(), 1);
        patient = service.addCheckIn(patientId, checkIn);
        secondAlarms = alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        alarmSecondSize = secondAlarms.size();
        //no alarm should be created yet
        assertEquals(alarmFirstSize + 3, alarmSecondSize);

        //sixth severe alarm
/*        timestamp.add(Calendar.HOUR_OF_DAY, 6);
        checkIn = createCheckInWithSoreThroatSymptom(timestamp.getTimeInMillis(), 2);
        patient = service.addCheckIn(patientId, checkIn);
        secondAlarms =  alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        alarmSecondSize = secondAlarms.size();
        //alarm should be created

        assertEquals(alarmFirstSize + 4, alarmSecondSize);
*/
        //test call to check-in controller to make sure whether there is no JPA or JSON problem
        CheckInSvcApi checkInSvcApi = getService(CheckInSvcApi.class);
        checkInSvcApi.findAll();
    }

    private CheckIn createCheckInWithSoreThroatSymptom(long timestamp, int severity) {
        List<SymptomTime> symptomTimes = new ArrayList<SymptomTime>();
        SymptomSvcApi symptomSvcApi = getService(SymptomSvcApi.class);
        SymptomTime symptomTime = new SymptomTime();
        //set symptom
        List<Symptom> symptoms = symptomSvcApi.findByType(ServiceUtils.SYMPTOM_TYPE_SORE_THROAT);
        symptomTime.setSymptom(symptoms.get(0));

        symptomTime.setSeverity(severity);
        symptomTime.setTimestamp(timestamp);
        symptomTimes.add(symptomTime);

        //attach to check-in
        CheckIn checkIn = new CheckIn();
        checkIn.setTimestamp(timestamp);
        checkIn.setSymptomTimes(symptomTimes);
        return checkIn;
    }

    @Test
    public void testEatDrinkAlarm() {
        //the patient we want to do the check-in
        long patientId = 1;
        Patient patient = service.findOne(patientId);
        assertNotNull(patient);

        //create the one symptom time for Sore Throat
        Calendar timestamp = new GregorianCalendar();
        //go 5 days and 1 hour back
        timestamp.add(Calendar.DAY_OF_MONTH, -5);
        timestamp.add(Calendar.HOUR_OF_DAY, -1);
        CheckIn checkIn = createCheckInWithEatDrinkSymptom(timestamp.getTimeInMillis(), 1);

        AlarmSvcApi alarmSvcApi = getService(AlarmSvcApi.class);
        List<Alarm> firstAlarms =  alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(firstAlarms);
        int alarmFirstSize = firstAlarms.size();
        patient = service.addCheckIn(patientId, checkIn);
        List<Alarm> secondAlarms =  alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        int alarmSecondSize = secondAlarms.size();

        //no alarm should be created yet
        assertEquals(alarmFirstSize, alarmSecondSize);

        timestamp.add(Calendar.HOUR_OF_DAY, 12);
        checkIn = createCheckInWithEatDrinkSymptom(timestamp.getTimeInMillis(), 1);
        patient = service.addCheckIn(patientId, checkIn);
        secondAlarms =  alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        alarmSecondSize = secondAlarms.size();
        //no alarm should be created yet
        assertEquals(alarmFirstSize, alarmSecondSize);

        //test severe alarm
        //first severe checkin
        timestamp.add(Calendar.HOUR_OF_DAY, 3);
        checkIn = createCheckInWithEatDrinkSymptom(timestamp.getTimeInMillis(), 2);
        patient = service.addCheckIn(patientId, checkIn);
        secondAlarms =  alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        alarmSecondSize = secondAlarms.size();
        //no alarm should be created yet
        assertEquals(alarmFirstSize, alarmSecondSize);
        //second severe alarm
        timestamp.add(Calendar.HOUR_OF_DAY, 12);
        checkIn = createCheckInWithEatDrinkSymptom(timestamp.getTimeInMillis(), 2);
        patient = service.addCheckIn(patientId, checkIn);
        secondAlarms =  alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        alarmSecondSize = secondAlarms.size();
        //alarm should be created
        assertEquals(alarmFirstSize + 1, alarmSecondSize);

        //third severe alarm
        timestamp.add(Calendar.HOUR_OF_DAY, 20);
        checkIn = createCheckInWithEatDrinkSymptom(timestamp.getTimeInMillis(), 2);
        patient = service.addCheckIn(patientId, checkIn);
        secondAlarms =  alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        alarmSecondSize = secondAlarms.size();
        //alarm should be created
        assertEquals(alarmFirstSize + 2, alarmSecondSize);

        //fourth moderate alarm
        timestamp.add(Calendar.HOUR_OF_DAY, 16);
        checkIn = createCheckInWithEatDrinkSymptom(timestamp.getTimeInMillis(), 1);
        patient = service.addCheckIn(patientId, checkIn);
        secondAlarms =  alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        alarmSecondSize = secondAlarms.size();
        //no alarm here
        assertEquals(alarmFirstSize + 2, alarmSecondSize);

        //fifth moderate alarm
        timestamp.add(Calendar.HOUR_OF_DAY, 12);
        checkIn = createCheckInWithEatDrinkSymptom(timestamp.getTimeInMillis(), 2);
        patient = service.addCheckIn(patientId, checkIn);
        secondAlarms =  alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        alarmSecondSize = secondAlarms.size();
        //no alarm here
        assertEquals(alarmFirstSize + 2, alarmSecondSize);
    }

    private CheckIn createCheckInWithEatDrinkSymptom(long timestamp, int severity) {
        List<SymptomTime> symptomTimes = new ArrayList<SymptomTime>();
        SymptomSvcApi symptomSvcApi = getService(SymptomSvcApi.class);
        SymptomTime symptomTime = new SymptomTime();
        //set symptom
        List<Symptom> symptoms = symptomSvcApi.findByType(ServiceUtils.SYMPTOM_TYPE_EAT_DRINK);
        symptomTime.setSymptom(symptoms.get(0));

        symptomTime.setSeverity(severity);
        symptomTime.setTimestamp(timestamp);
        symptomTimes.add(symptomTime);

        //attach to check-in
        CheckIn checkIn = new CheckIn();
        checkIn.setTimestamp(timestamp);
        checkIn.setSymptomTimes(symptomTimes);
        return checkIn;
    }

    @Override
    public Class<PatientSvcApi> getApiClass() {
        return PatientSvcApi.class;
    }
}
