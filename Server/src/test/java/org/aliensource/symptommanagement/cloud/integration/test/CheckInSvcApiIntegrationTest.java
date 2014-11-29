package org.aliensource.symptommanagement.cloud.integration.test;

import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.IntakeTime;
import org.aliensource.symptommanagement.cloud.repository.Medicament;
import org.aliensource.symptommanagement.cloud.repository.Medication;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.Symptom;
import org.aliensource.symptommanagement.cloud.repository.SymptomTime;
import org.aliensource.symptommanagement.cloud.service.CheckInSvcApi;
import org.aliensource.symptommanagement.cloud.service.MedicamentSvcApi;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.aliensource.symptommanagement.cloud.service.SymptomSvcApi;
import org.junit.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CheckInSvcApiIntegrationTest extends BaseSvcApiIntegrationTest<CheckInSvcApi> {

    @Test
    public void testSave() {
        CheckIn model = new CheckIn();
        model = service.save(model);

        //exists in list?
        List<CheckIn> models = readOnlyService.findAll();
        assertNotNull(models);
        assertTrue(models.size() > 0);

        //search by id
        assertNotNull(service.findOne(model.getId()));
    }

/*    @Test
    public void testCheckIn() {
        PatientSvcApi patientSvcApi = getService(PatientSvcApi.class);
        long patientId = 1;
        //the patient we want to do the check-in
        Patient patient = patientSvcApi.findOne(patientId);
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
        //checkIn.setPatient(patient);

        int beforeCheckInSize = patient.getCheckIns().size();
        checkIn = service.save(checkIn);

        patient = patientSvcApi.findOne(patientId);
        //check-in saved?
        assertEquals(beforeCheckInSize + 1, patient.getCheckIns().size());
        //get last check-in
        assertTrue(patient.getCheckIns().contains(checkIn));
        //symptom times saved?
        assertEquals(symptomTimes.size(), checkIn.getSymptomTimes().size());
        //intake times saved?
        assertEquals(intakeTimes.size(), checkIn.getIntakeTimes().size());
        //test call to check-in controller to make sure whether there is no JPA or JSON problem
        service.findAll();
    }
*/
    @Override
    public Class<CheckInSvcApi> getApiClass() {
        return CheckInSvcApi.class;
    }
}
