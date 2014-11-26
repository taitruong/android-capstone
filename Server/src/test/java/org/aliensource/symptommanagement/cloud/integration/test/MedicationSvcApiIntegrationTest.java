package org.aliensource.symptommanagement.cloud.integration.test;

import org.aliensource.symptommanagement.cloud.repository.Medication;
import org.aliensource.symptommanagement.cloud.service.MedicationSvcApi;
import org.aliensource.symptommanagement.cloud.service.MedicationSvcApi;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MedicationSvcApiIntegrationTest extends BaseSvcApiIntegrationTest<MedicationSvcApi> {

    @Test
    public void testFindAll() {
        List<Medication> models = readOnlyService.findAll();
        assertNotNull(models);
        assertTrue(models.size() > 0);
        //make sure the medicaments are eagerly fetched
        for (Medication model: models) {
            assertNotNull(model.getMedicament());
        }
    }

    @Test
    public void testFindOne() {
        long id = 1;
        Medication Medication = readOnlyService.findOne(id);
        assertNotNull(Medication);
        assertEquals(id, Medication.getId());
    }

    @Override
    public Class<MedicationSvcApi> getApiClass() {
        return MedicationSvcApi.class;
    }
}
