package org.aliensource.symptommanagement.cloud.integration.test;

import org.aliensource.symptommanagement.cloud.TestUtils;
import org.aliensource.symptommanagement.cloud.repository.Doctor;
import org.aliensource.symptommanagement.cloud.service.DoctorSvcApi;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DoctorSvcApiIntegrationTest extends BaseSvcApiIntegrationTest<DoctorSvcApi> {

	private final Doctor model = TestUtils.randomDoctorWithoutPatient();

	@Test
	public void testAdd() throws Exception {
		// Add the model
		Doctor newModel = service.save(model);

        List<Doctor> data = service.findAll();
        assertNotNull(data);
		assertTrue(newModel + ": not in " + data, data.contains(newModel));
    }

    @Test
    public void testFindByUsername() {
        List<Doctor> model = readOnlyService.findByUsername("doctor1");
        assertNotNull(model);
        assertTrue(model.size() == 1);
        assertEquals("doctor1", model.get(0).getUsername());
    }

    @Test
    public void testFindAll() {
        List<Doctor> models = readOnlyService.findAll();
        assertNotNull(models);
        assertTrue(models.size() > 0);
    }

    @Override
    public Class<DoctorSvcApi> getApiClass() {
        return DoctorSvcApi.class;
    }
}
