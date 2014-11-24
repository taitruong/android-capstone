package org.aliensource.symptommanagement.cloud.integration.test;

import com.google.gson.JsonObject;

import org.aliensource.symptommanagement.client.EasyHttpClient;
import org.aliensource.symptommanagement.client.oauth.SecuredRestBuilder;
import org.aliensource.symptommanagement.client.oauth.SecuredRestException;
import org.aliensource.symptommanagement.cloud.TestUtils;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.dto.PatientDTO;
import org.aliensource.symptommanagement.cloud.repository.dto.SpringDataRestDTO;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;
import org.aliensource.symptommanagement.cloud.service.SecurityService;
import org.junit.Test;

import java.util.UUID;

import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.client.ApacheClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PatientSvcApiIntegrationTest extends BaseSvcApiIntegrationTest<PatientSvcApi> {

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
		// Add the video
		service.add(model);

        SpringDataRestDTO<PatientDTO> data = service.findAll();
        assertNotNull(data);
        assertNotNull(data.getEmbedded());
        assertNotNull(data.getEmbedded().getModels());
		assertTrue(data.getEmbedded().getModels().contains(model));
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
			invalidClientService.add(model);

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

		SpringDataRestDTO<PatientDTO> data = readOnlyService.findAll();
		assertNotNull(data);

		try {
			readOnlyService.add(model);

			fail("The server should have prevented the client from adding a model"
					+ " because it is using a read-only client ID");
		} catch (RetrofitError e) {
			JsonObject body = (JsonObject)e.getBodyAs(JsonObject.class);
			assertEquals("insufficient_scope", body.get("error").getAsString());
		}
	}

    @Test
    public void testFindByUsername() {
        SpringDataRestDTO<PatientDTO> model = readOnlyService.findByUsername("patient1");
        assertNotNull(model);
        assertNotNull(model.getEmbedded());
        assertNotNull(model.getEmbedded().getModels());
        assertTrue(model.getEmbedded().getModels().size() == 1);
        assertEquals("patient1", model.getEmbedded().getModels().get(0).getUsername());
    }

    @Test
    public void testFindAll() {
        SpringDataRestDTO<PatientDTO> models = readOnlyService.findAll();
        assertNotNull(models);
        assertNotNull(models.getEmbedded());
        assertNotNull(models.getEmbedded().getModels());
        assertTrue(models.getEmbedded().getModels().size() > 0);
    }

    @Override
    public Class<PatientSvcApi> getApiClass() {
        return PatientSvcApi.class;
    }
}
