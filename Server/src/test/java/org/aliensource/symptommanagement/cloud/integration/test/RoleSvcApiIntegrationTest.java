package org.aliensource.symptommanagement.cloud.integration.test;

import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.Role;
import org.aliensource.symptommanagement.cloud.service.RoleSvcApi;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RoleSvcApiIntegrationTest extends BaseSvcApiIntegrationTest<RoleSvcApi> {

    @Override
    public Class<RoleSvcApi> getApiClass() {
        return RoleSvcApi.class;
    }

    @Test
    public void testSave() {
        Role model = new Role();
        model = service.save(model);

        //exists in list?
        List<Role> models = readOnlyService.findAll();
        assertNotNull(models);
        assertTrue(models.size() > 0);

        //search by id
        assertNotNull(service.findOne(model.getId()));
    }

}
