package org.aliensource.symptommanagement.cloud.integration.test;

import org.aliensource.symptommanagement.cloud.repository.Role;
import org.aliensource.symptommanagement.cloud.repository.Symptom;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.aliensource.symptommanagement.cloud.service.SymptomSvcApi;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SymptomSvcApiIntegrationTest extends BaseSvcApiIntegrationTest<SymptomSvcApi> {

    @Test
    public void testFindAll() {
        List<Symptom> models = readOnlyService.findAll();
        assertTrue(models.size() > 0);
    }

    @Test
    public void testFindByType() {
        List<Symptom> model = readOnlyService.findByType(ServiceUtils.SYMPTOM_TYPE_SORE_THROAT);
        assertNotNull(model);
        assertTrue(model.size() == 1);
        assertEquals(ServiceUtils.SYMPTOM_TYPE_SORE_THROAT, model.get(0).getType());
    }

    @Test
    public void testSave() {
        Symptom model = new Symptom();
        model = service.save(model);

        //exists in list?
        List<Symptom> models = readOnlyService.findAll();
        assertNotNull(models);
        assertTrue(models.size() > 0);

        //search by id
        assertNotNull(service.findOne(model.getId()));
    }

    @Override
    public Class<SymptomSvcApi> getApiClass() {
        return SymptomSvcApi.class;
    }
}
