package org.aliensource.symptommanagement.cloud.integration.test;

import org.aliensource.symptommanagement.cloud.repository.dto.PatientDTO;
import org.aliensource.symptommanagement.cloud.repository.dto.RoleDTO;
import org.aliensource.symptommanagement.cloud.repository.dto.SpringDataRestDTO;
import org.aliensource.symptommanagement.cloud.repository.dto.SymptomDTO;
import org.aliensource.symptommanagement.cloud.service.RoleSvcApi;
import org.aliensource.symptommanagement.cloud.service.ServiceUtils;
import org.aliensource.symptommanagement.cloud.service.SymptomSvcApi;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SymptomSvcApiIntegrationTest extends BaseSvcApiIntegrationTest<SymptomSvcApi> {

    @Test
    public void testFindAll() {
        SpringDataRestDTO<SymptomDTO> models = readOnlyService.findAll();
        assertNotNull(models);
        assertNotNull(models.getEmbedded());
        assertNotNull(models.getEmbedded().getModels());
        assertTrue(models.getEmbedded().getModels().size() > 0);
    }

    @Test
    public void testFindByType() {
        SpringDataRestDTO<SymptomDTO> model = readOnlyService.findByType(ServiceUtils.SYMPTOM_TYPE_SORE_THROAT);
        assertNotNull(model);
        assertNotNull(model.getEmbedded());
        assertNotNull(model.getEmbedded().getModels());
        assertTrue(model.getEmbedded().getModels().size() == 1);
        assertEquals(ServiceUtils.SYMPTOM_TYPE_SORE_THROAT, model.getEmbedded().getModels().get(0).getType());
    }

    @Override
    public Class<SymptomSvcApi> getApiClass() {
        return SymptomSvcApi.class;
    }
}
