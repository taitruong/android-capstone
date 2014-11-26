package org.aliensource.symptommanagement.cloud.integration.test;

import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.IntakeTime;
import org.aliensource.symptommanagement.cloud.service.IntakeTimeSvcApi;
import org.aliensource.symptommanagement.cloud.service.IntakeTimeSvcApi;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class IntakeTimeSvcApiIntegrationTest extends BaseSvcApiIntegrationTest<IntakeTimeSvcApi> {

    @Test
    public void testSave() {
        IntakeTime model = new IntakeTime();
        model = service.save(model);

        //exists in list?
        List<IntakeTime> models = readOnlyService.findAll();
        assertNotNull(models);
        assertTrue(models.size() > 0);

        //search by id
        assertNotNull(service.findOne(model.getId()));
    }

    @Override
    public Class<IntakeTimeSvcApi> getApiClass() {
        return IntakeTimeSvcApi.class;
    }
}
