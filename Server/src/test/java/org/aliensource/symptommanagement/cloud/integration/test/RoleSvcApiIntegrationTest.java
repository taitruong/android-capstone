package org.aliensource.symptommanagement.cloud.integration.test;

import com.google.gson.JsonObject;

import org.aliensource.symptommanagement.client.EasyHttpClient;
import org.aliensource.symptommanagement.client.oauth.SecuredRestBuilder;
import org.aliensource.symptommanagement.client.oauth.SecuredRestException;
import org.aliensource.symptommanagement.cloud.TestUtils;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.Role;
import org.aliensource.symptommanagement.cloud.repository.dto.PatientDTO;
import org.aliensource.symptommanagement.cloud.repository.dto.RoleDTO;
import org.aliensource.symptommanagement.cloud.repository.dto.SpringDataRestDTO;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;
import org.aliensource.symptommanagement.cloud.service.RoleSvcApi;
import org.aliensource.symptommanagement.cloud.service.SecurityService;
import org.junit.Test;

import java.util.Collection;
import java.util.UUID;

import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.client.ApacheClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RoleSvcApiIntegrationTest extends BaseSvcApiIntegrationTest<RoleSvcApi> {

    @Test
    public void testFindAll() {
        SpringDataRestDTO<RoleDTO> models = readOnlyService.findAll();
        assertNotNull(models);
        assertNotNull(models.getEmbedded());
        assertNotNull(models.getEmbedded().getModels());
        assertTrue(models.getEmbedded().getModels().size() > 0);
    }

    @Override
    public Class<RoleSvcApi> getApiClass() {
        return RoleSvcApi.class;
    }
}
