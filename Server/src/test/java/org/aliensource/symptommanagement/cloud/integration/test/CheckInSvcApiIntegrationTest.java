package org.aliensource.symptommanagement.cloud.integration.test;

import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.Medicament;
import org.aliensource.symptommanagement.cloud.service.CheckInSvcApi;
import org.aliensource.symptommanagement.cloud.service.MedicamentSvcApi;
import org.junit.Test;

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

    @Override
    public Class<CheckInSvcApi> getApiClass() {
        return CheckInSvcApi.class;
    }
}
