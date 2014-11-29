package org.aliensource.symptommanagement.cloud.integration.test;

import org.aliensource.symptommanagement.cloud.repository.Alarm;
import org.aliensource.symptommanagement.cloud.repository.Role;
import org.aliensource.symptommanagement.cloud.service.AlarmSvcApi;
import org.aliensource.symptommanagement.cloud.service.RoleSvcApi;
import org.junit.Test;

import java.util.List;

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
        model = service.save(model);

        //exists in list?
        List<Alarm> models = readOnlyService.findAll();
        assertNotNull(models);
        assertTrue(models.size() > 0);

        //search by id
        assertNotNull(service.findOne(model.getId()));
    }

}
