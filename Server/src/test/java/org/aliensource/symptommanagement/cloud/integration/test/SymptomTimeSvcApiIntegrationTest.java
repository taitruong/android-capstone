package org.aliensource.symptommanagement.cloud.integration.test;

import org.aliensource.symptommanagement.cloud.repository.IntakeTime;
import org.aliensource.symptommanagement.cloud.repository.SymptomTime;
import org.aliensource.symptommanagement.cloud.service.IntakeTimeSvcApi;
import org.aliensource.symptommanagement.cloud.service.SymptomTimeSvcApi;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SymptomTimeSvcApiIntegrationTest extends BaseSvcApiIntegrationTest<SymptomTimeSvcApi> {

    @Test
    public void testSave() {
        SymptomTime model = new SymptomTime();
        model = service.save(model);

        //exists in list?
        List<SymptomTime> models = readOnlyService.findAll();
        assertNotNull(models);
        assertTrue(models.size() > 0);

        //search by id
        assertNotNull(service.findOne(model.getId()));
    }

    @Override
    public Class<SymptomTimeSvcApi> getApiClass() {
        return SymptomTimeSvcApi.class;
    }
}
