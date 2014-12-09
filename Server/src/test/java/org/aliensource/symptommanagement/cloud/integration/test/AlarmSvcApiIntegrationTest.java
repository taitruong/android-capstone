package org.aliensource.symptommanagement.cloud.integration.test;

import org.aliensource.symptommanagement.cloud.repository.Alarm;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.PatientAlarmDTO;
import org.aliensource.symptommanagement.cloud.repository.Role;
import org.aliensource.symptommanagement.cloud.repository.Symptom;
import org.aliensource.symptommanagement.cloud.service.AlarmSvcApi;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;
import org.aliensource.symptommanagement.cloud.service.RoleSvcApi;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.aliensource.symptommanagement.cloud.service.SymptomSvcApi;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AlarmSvcApiIntegrationTest extends BaseSvcApiIntegrationTest<AlarmSvcApi> {

    @Override
    public Class<AlarmSvcApi> getApiClass() {
        return AlarmSvcApi.class;
    }

    @Test
    public void testSave() {
        long patientId = 111;
        List<Alarm> alarms = service.findByPatientId(patientId);
        int sizeBefore = alarms.size();

        SymptomSvcApi symptomSvcApi = getService(SymptomSvcApi.class);
        List<Symptom> symptoms = symptomSvcApi.findByType(ServiceUtils.SYMPTOM_TYPE_SORE_THROAT);
        assertNotNull(ServiceUtils.SYMPTOM_TYPE_SORE_THROAT + " not found", symptoms);
        assertEquals(1, symptoms.size());

        PatientSvcApi patientSvcApi = getService(PatientSvcApi.class);
        Patient patient = patientSvcApi.findOne(patientId);
        assertNotNull(patient);
        assertEquals(patientId, patient.getId());

        Alarm model = new Alarm();
        model.setPatientId(patient.getId());
        model.setSymptom(symptoms.get(0));

        model = service.save(model);

        //exists in list?
        List<Alarm> models = readOnlyService.findAll();
        assertNotNull(models);
        assertTrue(models.size() > 0);

        //search by id
        assertNotNull(service.findOne(model.getId()));

        alarms = service.findByPatientId(patientId);
        assertNotNull(alarms);
        assertEquals(sizeBefore + 1, alarms.size());
    }

    @Test
    public void testGetPatientAlarmsByDoctorUserName() {
        Calendar yesterday = new GregorianCalendar();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);

        Alarm model = new Alarm();
        model.setStart(yesterday.getTimeInMillis());
        model.setPatientId(2);
        model = service.save(model);

        long now = System.currentTimeMillis();
        model = new Alarm();
        model.setStart(now);
        model.setPatientId(2);
        model = service.save(model);

        List<PatientAlarmDTO> patientToAlarm = service.getPatientAlarmsByDoctorUserName("doctor2");

        assertNotNull(patientToAlarm);
        assertEquals("Alarms: " + patientToAlarm, 1, patientToAlarm.size());

        assertEquals("Not the latest alarm", now, patientToAlarm.get(0).alarm.getStart());
    }

}
