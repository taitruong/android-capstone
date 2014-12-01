package org.aliensource.symptommanagement.cloud.integration.test;

import org.aliensource.symptommanagement.cloud.repository.Alarm;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.PatientAlarmDTO;
import org.aliensource.symptommanagement.cloud.repository.Role;
import org.aliensource.symptommanagement.cloud.service.AlarmSvcApi;
import org.aliensource.symptommanagement.cloud.service.RoleSvcApi;
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
        Alarm model = new Alarm();
        model.setPatientId(111);
        model = service.save(model);

        //exists in list?
        List<Alarm> models = readOnlyService.findAll();
        assertNotNull(models);
        assertTrue(models.size() > 0);

        //search by id
        assertNotNull(service.findOne(model.getId()));

        List<Alarm> alarms = service.findByPatientId(111);
        assertNotNull(alarms);
        assertEquals(1, alarms.size());
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
