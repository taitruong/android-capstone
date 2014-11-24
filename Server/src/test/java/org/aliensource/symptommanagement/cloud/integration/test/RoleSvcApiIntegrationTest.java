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

public class RoleSvcApiIntegrationTest {

	private final String USERNAME = "patient1";
	private final String PASSWORD = "pass";
	private final String CLIENT_ID = "mobile";
	private final String READ_ONLY_CLIENT_ID = "mobileReader";

	private final String TEST_URL = "https://localhost:8443";

	private RoleSvcApi readOnlyService = new SecuredRestBuilder()
			.setLoginEndpoint(TEST_URL + SecurityService.TOKEN_PATH)
			.setUsername(USERNAME)
			.setPassword(PASSWORD)
			.setClientId(READ_ONLY_CLIENT_ID)
			.setClient(new ApacheClient(new EasyHttpClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(RoleSvcApi.class);

    @Test
    public void testFindAll() {
        SpringDataRestDTO<RoleDTO> models = readOnlyService.findAll();
        assertNotNull(models);
        assertNotNull(models.getEmbedded());
        assertNotNull(models.getEmbedded().getModels());
        for (Role role: models.getEmbedded().getModels()) {
            System.out.println(">>>>" + role.getId() + " " + role.getName());
        }

    }

}
