package org.aliensource.symptommanagement.android.test;

import junit.framework.TestCase;

import org.aliensource.symptommanagement.client.service.PatientSvc;
import org.aliensource.symptommanagement.cloud.TestUtils;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;

import java.util.Collection;

public class PatientSvcIntegrationTest extends TestCase {

    private String localhost_;

    private PatientSvcApi service;

    private final Patient model = TestUtils.randomPatient();

    @Override
    public void setUp() throws Exception {
        localhost_ = TestUtils.findTheRealLocalhostAddress();
        assertNotNull("Is the server running? Unable to connect to the server...", localhost_);

        service = PatientSvc.init(TestUtils.PROTOCOL + "://" + localhost_ + ":" + TestUtils.PORT, TestUtils.USER_DOCTOR1, TestUtils.USER_DOCTOR1_PASS);
        assertNotNull("This shouldn't happen...but it did...@#$!@#$", service);
    }

    public void testFindAll(){
        Collection<Patient> videos = service.findAll();
        assertNotNull(videos);
    }

    public void testAddRemove(){
        service.add(model);
        Collection<Patient> data = service.findAll();
        assertTrue(data.contains(model));

        deleteAllVideos(data);

        data = service.findAll();
        assertTrue(!data.contains(model));
    }

    private void deleteAllVideos(Collection<Patient> data) {
        for(Patient model: data){
            service.delete(model.getId());
        }
    }

    public void testFindByUserName() {
        service.add(model);
        Collection<Patient> withTitle = service.findByUsername(model.getUsername());
        assertTrue(withTitle.size() > 0);
        for(Patient m: withTitle){
            assertEquals(model.getUsername(), m.getUsername());
        }

        String titleThatShouldntExist = model.getUsername() + TestUtils.randomString();
        withTitle = service.findByUsername(titleThatShouldntExist);
        assertTrue(withTitle.size() == 0);

        withTitle = service.findByUsername(model.getUsername());
        deleteAllVideos(withTitle);
        withTitle = service.findByUsername(model.getUsername());
        assertTrue(withTitle.size() == 0);
    }

}
