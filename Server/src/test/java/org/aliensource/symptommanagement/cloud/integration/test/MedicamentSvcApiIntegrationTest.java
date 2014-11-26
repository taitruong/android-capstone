package org.aliensource.symptommanagement.cloud.integration.test;

import org.aliensource.symptommanagement.cloud.repository.Medicament;
import org.aliensource.symptommanagement.cloud.service.MedicamentSvcApi;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MedicamentSvcApiIntegrationTest extends BaseSvcApiIntegrationTest<MedicamentSvcApi> {

    @Test
    public void testFindAll() {
        List<Medicament> models = readOnlyService.findAll();
        assertNotNull(models);
        assertTrue(models.size() > 0);
    }

    @Test
    public void testFindOne() {
        long id = 1;
        Medicament medicament = readOnlyService.findOne(id);
        assertNotNull(medicament);
        assertEquals(id, medicament.getId());
        assertNotNull(medicament.getName());
    }

    @Override
    public Class<MedicamentSvcApi> getApiClass() {
        return MedicamentSvcApi.class;
    }
}
