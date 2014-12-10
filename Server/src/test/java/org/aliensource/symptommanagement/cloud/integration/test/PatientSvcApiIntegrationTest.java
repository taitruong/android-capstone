package org.aliensource.symptommanagement.cloud.integration.test;

import com.google.gson.JsonObject;

import org.aliensource.symptommanagement.DateTimeUtils;
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
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.aliensource.symptommanagement.cloud.service.SymptomSvcApi;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private CheckInSvcApi checkInSvcApi = getService(CheckInSvcApi.class);

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

        Calendar timestamp = new GregorianCalendar();
        //get last check-in and use this timestamp to continue a check-in at that time
        CheckInSvcApi checkInSvcApi = getService(CheckInSvcApi.class);
        List<CheckIn> checkIns = checkInSvcApi.findByPatientId(patientId);
        assertNotNull(checkIns);
        if (checkIns.size() > 0) {
            long lastCheckInTime = checkIns.get(checkIns.size() - 1).getTimestamp();
            timestamp.setTime(new Date((lastCheckInTime)));
            //add another hour
            timestamp.add(Calendar.HOUR_OF_DAY, 1);
        }

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
            symptomTime.setTimestamp(timestamp.getTimeInMillis());

            symptomTimes.add(symptomTime);
        }

        //create the intake times for the medications
        List<IntakeTime> intakeTimes = new ArrayList<IntakeTime>();
        for (Medication medication : patient.getMedications()) {
            IntakeTime intakeTime = new IntakeTime();
            intakeTime.setMedicament(medication.getMedicament());
            intakeTime.setTimestamp(timestamp.getTimeInMillis());
            intakeTimes.add(intakeTime);
        }

        //attach to check-in
        CheckIn checkIn = new CheckIn();
        checkIn.setTimestamp(timestamp.getTimeInMillis());
        checkIn.setIntakeTimes(intakeTimes);
        checkIn.setSymptomTimes(symptomTimes);

        int beforeCheckInSize = checkInSvcApi.findByPatientId(patient.getId()).size();

        patient = service.addCheckIn(patientId, checkIn);

        //check-in saved?
        List<CheckIn> result = checkInSvcApi.findByPatientId(patient.getId());
        assertEquals(
                beforeCheckInSize + 1, result.size());
        //get last check-in
        checkIn = result.get(result.size() - 1);
        //symptom times saved?
        assertEquals(symptomTimes.size(), checkIn.getSymptomTimes().size());
        //intake times saved?
        assertEquals(intakeTimes.size(), checkIn.getIntakeTimes().size());
        //test call to check-in controller to make sure whether there is no JPA or JSON problem
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
    public void testSoreThroatMouthPainAlarm() {
        //the patient we want to do the check-in
        long patientId = 1;
        Patient patient = service.findOne(patientId);
        assertNotNull(patient);

        //create the check-in and symptom time for sore throat/mouth pain
        Calendar timestamp = new GregorianCalendar();
        //get last check-in and use this timestamp to continue a check-in at that time
        CheckInSvcApi checkInSvcApi = getService(CheckInSvcApi.class);
        List<CheckIn> checkIns = checkInSvcApi.findByPatientId(patientId);
        assertNotNull(checkIns);
        if (checkIns.size() > 0) {
            long lastCheckInTime = checkIns.get(checkIns.size() - 1).getTimestamp();
            timestamp.setTime(new Date((lastCheckInTime)));
            //add another hour
            timestamp.add(Calendar.HOUR_OF_DAY, 1);
            for (CheckIn checkIn: checkIns) {
                System.out.println(">>>> check-in at " + DateTimeUtils.FORMAT_DDMMYYYY_HHMM.format(new Date(checkIn.getTimestamp())));
            }
            System.out.println(">>>> start at " + DateTimeUtils.FORMAT_DDMMYYYY_HHMM.format(timestamp.getTime()));
        }
        CheckIn checkIn = createCheckInWithSoreThroatSymptom(timestamp.getTimeInMillis(), 0);

        AlarmSvcApi alarmSvcApi = getService(AlarmSvcApi.class);
        List<Alarm> firstAlarms =  alarmSvcApi.findByPatientId(patientId);
        assertNotNull(firstAlarms);
        int alarmFirstSize = firstAlarms.size();

        patient = service.addCheckIn(patientId, checkIn);
        List<Alarm> secondAlarms = alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        int alarmSecondSize = secondAlarms.size();

        //no alarm should be created yet
        assertEquals(alarmFirstSize, alarmSecondSize);

        timestamp.add(Calendar.HOUR_OF_DAY, 12);
        checkIn = createCheckInWithSoreThroatSymptom(timestamp.getTimeInMillis(), 1);
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
        //severity level 2 over 12 hours: alarm should be created
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
        timestamp.add(Calendar.HOUR_OF_DAY, 12);
        checkIn = createCheckInWithSoreThroatSymptom(timestamp.getTimeInMillis(), 1);
        patient = service.addCheckIn(patientId, checkIn);
        secondAlarms = alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        alarmSecondSize = secondAlarms.size();
        //severity goes back from 2 to 1 in 12 hours: alarm should not be created
        assertEquals(alarmFirstSize + 2, alarmSecondSize);

        //fifth moderate alarm
        timestamp.add(Calendar.HOUR_OF_DAY, 16);
        checkIn = createCheckInWithSoreThroatSymptom(timestamp.getTimeInMillis(), 2);
        patient = service.addCheckIn(patientId, checkIn);
        secondAlarms = alarmSvcApi.findByPatientId(patient.getId());
        assertNotNull(secondAlarms);
        alarmSecondSize = secondAlarms.size();
        //severity increases from 1 to 2 in 16 hours: alarm should be created
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

        //create the check-in and symptom time for eat/drink
        Calendar timestamp = new GregorianCalendar();
        //get last check-in and use this timestamp to continue a check-in at that time
        CheckInSvcApi checkInSvcApi = getService(CheckInSvcApi.class);
        List<CheckIn> checkIns = checkInSvcApi.findByPatientId(patientId);
        assertNotNull(checkIns);
        if (checkIns.size() > 0) {
            long lastCheckInTime = checkIns.get(checkIns.size() - 1).getTimestamp();
            timestamp.setTime(new Date((lastCheckInTime)));
            //add another hour
            timestamp.add(Calendar.HOUR_OF_DAY, 1);
        }
        CheckIn checkIn = createCheckInWithEatDrinkSymptom(timestamp.getTimeInMillis(), 0);

        AlarmSvcApi alarmSvcApi = getService(AlarmSvcApi.class);
        List<Alarm> firstAlarms =  alarmSvcApi.findByPatientId(patientId);
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

    @Test
    public void testGetSymptomTimesByEatDrinkOrSoreThroatMouthPain() {
        List<SymptomTime>[] result = service.getSymptomTimesByEatDrinkOrSoreThroatMouthPain(1);
        assertNotNull(result);
    }

    @Override
    public Class<PatientSvcApi> getApiClass() {
        return PatientSvcApi.class;
    }
}
