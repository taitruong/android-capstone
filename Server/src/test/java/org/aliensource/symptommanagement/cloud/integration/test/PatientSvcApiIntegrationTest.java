package org.aliensource.symptommanagement.cloud.integration.test;

import com.google.gson.JsonObject;

import org.aliensource.symptommanagement.client.oauth.SecuredRestException;
import org.aliensource.symptommanagement.cloud.TestUtils;
import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.IntakeTime;
import org.aliensource.symptommanagement.cloud.repository.Medicament;
import org.aliensource.symptommanagement.cloud.repository.Medication;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.Symptom;
import org.aliensource.symptommanagement.cloud.repository.SymptomTime;
import org.aliensource.symptommanagement.cloud.service.CheckInSvcApi;
import org.aliensource.symptommanagement.cloud.service.MedicationSvcApi;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.aliensource.symptommanagement.cloud.service.SymptomSvcApi;
import org.junit.Test;

import java.util.ArrayList;
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
			JsonObject body = (JsonObject)e.getBodyAs(JsonObject.class);
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
        data = readOnlyService.findByDoctorUsernameAndFilter("doctor1", "First");
        assertNotNull(data);
        assertTrue(data.size() > 0);
        data = readOnlyService.findByDoctorUsernameAndFilter("doctor1", "Last");
        assertNotNull(data);
        assertTrue(data.size() > 0);
        data = readOnlyService.findByDoctorUsernameAndFilter("doctor1", "medical-record");
        assertNotNull(data);
        assertTrue(data.size() > 0);
        data = readOnlyService.findByDoctorUsernameAndFilter("doctor1", "record");
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
        Patient patient = service.findOne(1);
        assertNotNull(patient);

        //create the symptom times
        List<SymptomTime> symptomTimes = new ArrayList<SymptomTime>();
        SymptomSvcApi symptomSvcApi = getService(SymptomSvcApi.class);
        for (String type: new String[]{ServiceUtils.SYMPTOM_TYPE_SORE_THROAT, ServiceUtils.SYMPTOM_TYPE_EAT_DRINK}) {
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
        for (Medication medication: patient.getMedications()) {
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

        patient.getCheckIns().add(checkIn);
        patient = service.save(patient);

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
    public void testAddMedication() {
        Patient patient = service.save(TestUtils.randomPatientWithoutDoctorsAndRoles());
        assertNotNull(patient);
        patient = service.addMedicamentForPatient(patient.getId(), 1);
        assertNotNull(patient);
        assertEquals(1, patient.getMedications().size());
        assertEquals(1, patient.getMedications().get(0).getMedicament().getId());
    }

    @Test
    public void testDeletedMedication() {
        long id = 1;

        Patient patient = service.findOne(id);
        assertNotNull(patient);
        assertNotNull(patient.getMedications());
        assertTrue(patient.getMedications().size() > 0);

        //remove medication from patient
        Medication medication = patient.getMedications().get(0);
        Medicament medicament = medication.getMedicament();
        //remove medication from patient
        patient = service.deleteMedicationForPatient(patient.getId(), medication.getId());
        assertNotNull(patient);
        assertNotNull(patient.getMedications());
        boolean found = false;
        for (Medication m: patient.getMedications()) {
            if (m.getMedicament().getId() == medicament.getId()) {
                found = true;
                break;
            }
        }
        assertFalse(found);
/*        MedicationSvcApi medicationSvcApi = getService(MedicationSvcApi.class);
        Medication medicationFromSvc = medicationSvcApi.findOne(medication.getId());
        assertNull(medicationFromSvc);
*/
        //save back
        patient.getMedications().add(medication);
        //TODO reset id to zero after fix why medication is not deleted only reference to patient
        patient = service.save(patient);
        assertNotNull(patient);
        assertNotNull(patient.getMedications());
        found = false;
        for (Medication m: patient.getMedications()) {
            if (m.getMedicament().getId() == medicament.getId()) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Override
    public Class<PatientSvcApi> getApiClass() {
        return PatientSvcApi.class;
    }
}
