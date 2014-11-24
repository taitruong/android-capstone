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

public class PatientSvcApiIntegrationTest {

	private final String USERNAME = "patient1";
	private final String PASSWORD = "pass";
	private final String CLIENT_ID = "mobile";
	private final String READ_ONLY_CLIENT_ID = "mobileReader";

	private final String TEST_URL = "https://localhost:8443";

	private PatientSvcApi patientService = new SecuredRestBuilder()
			.setLoginEndpoint(TEST_URL + SecurityService.TOKEN_PATH)
			.setUsername(USERNAME)
			.setPassword(PASSWORD)
			.setClientId(CLIENT_ID)
			.setClient(new ApacheClient(new EasyHttpClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(PatientSvcApi.class);

	private PatientSvcApi readOnlyPatientService = new SecuredRestBuilder()
			.setLoginEndpoint(TEST_URL + SecurityService.TOKEN_PATH)
			.setUsername(USERNAME)
			.setPassword(PASSWORD)
			.setClientId(READ_ONLY_CLIENT_ID)
			.setClient(new ApacheClient(new EasyHttpClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(PatientSvcApi.class);

	private PatientSvcApi invalidClientPatientService = new SecuredRestBuilder()
			.setLoginEndpoint(TEST_URL + SecurityService.TOKEN_PATH)
			.setUsername(UUID.randomUUID().toString())
			.setPassword(UUID.randomUUID().toString())
			.setClientId(UUID.randomUUID().toString())
			.setClient(new ApacheClient(new EasyHttpClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(PatientSvcApi.class);

	private Patient model = TestUtils.randomPatientWithoutDoctorsAndRoles();

	/**
	 * This test creates and adds, adds the Video to the VideoSvc, and then
	 * checks that the Video is included in the list when getVideoList() is
	 * called.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAdd() throws Exception {
        SpringDataRestDTO<PatientDTO> data = patientService.findAll();
        for (Patient patient: data.getEmbedded().getModels()) {
            System.out.println(">>>> user " + model);
        }
		// Add the video
        System.out.println(">>> adding " + model);
		patientService.add(model);

		// We should get back the video that we added above
		data = patientService.findAll();
        assertNotNull(data);
        assertNotNull(data.getEmbedded());
        assertNotNull(data.getEmbedded().getModels());
		assertTrue(data.getEmbedded().getModels().contains(model));
        for (Patient patient: data.getEmbedded().getModels()) {
            System.out.println(">>>> user " + model);
        }


    }

	/**
	 * This test ensures that clients with invalid credentials cannot get
	 * access to videos.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAccessDeniedWithIncorrectCredentials() throws Exception {

		try {
			// Add the video
			invalidClientPatientService.add(model);

			fail("The server should have prevented the client from adding a video"
					+ " because it presented invalid client/user credentials");
		} catch (RetrofitError e) {
			assert (e.getCause() instanceof SecuredRestException);
		}
	}
	
	/**
	 * This test ensures that read-only clients can access the video list
	 * but not add new videos.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testReadOnlyClientAccess() throws Exception {

		SpringDataRestDTO<PatientDTO> data = readOnlyPatientService.findAll();
		assertNotNull(data);

		try {
			readOnlyPatientService.add(model);

			fail("The server should have prevented the client from adding a model"
					+ " because it is using a read-only client ID");
		} catch (RetrofitError e) {
			JsonObject body = (JsonObject)e.getBodyAs(JsonObject.class);
			assertEquals("insufficient_scope", body.get("error").getAsString());
		}
	}

    @Test
    public void testFindByUsername() {
        SpringDataRestDTO<PatientDTO> model = readOnlyPatientService.findByUsername("patient1");
        assertNotNull(model);
        assertNotNull(model.getEmbedded());
        assertNotNull(model.getEmbedded().getModels());
        assertTrue(model.getEmbedded().getModels().size() == 1);
        assertEquals("patient1", model.getEmbedded().getModels().get(0).getUsername());
    }

    @Test
    public void testFindAll() {
        SpringDataRestDTO<PatientDTO> models = readOnlyPatientService.findAll();
        assertNotNull(models);
        assertNotNull(models.getEmbedded());
        assertNotNull(models.getEmbedded().getModels());
        assertTrue(models.getEmbedded().getModels().size() > 0);
    }

}
