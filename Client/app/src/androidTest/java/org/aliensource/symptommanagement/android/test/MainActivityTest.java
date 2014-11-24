package org.aliensource.symptommanagement.android.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.android.main.MainActivity;
import org.aliensource.symptommanagement.client.service.PatientSvc;
import org.aliensource.symptommanagement.cloud.TestUtils;
import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.service.PatientSvcApi;

import java.util.Collection;

/**
 * This test assumes that you are running integration testing against
 * a local version of the server and using the emulator. The server
 * must already be running before you launch this test. Since different
 * emulators have different addresses to represent the "real" localhost,
 * the test uses a helper method to derive the true localhost address.
 *
 *
 * Created by jules on 10/6/14.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private PatientSvcApi patientSvc;

    private MainActivity activity_;

    private ListView patientList;

    private final Patient model = TestUtils.randomPatient();

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        String localhost = TestUtils.findTheRealLocalhostAddress();
        assertNotNull(localhost);

        patientSvc = PatientSvc.init(TestUtils.PROTOCOL + "://" + localhost + ":" + TestUtils.PORT, TestUtils.USER_DOCTOR1, TestUtils.USER_DOCTOR1_PASS);
        assertNotNull(patientSvc);

        activity_ = getActivity();
        assertNotNull(activity_);

        patientList = (ListView)activity_.findViewById(R.id.videoList);
        assertNotNull(patientList);
    }

    public void testVideoListIsPopulatedCorrectly() throws Exception {
        patientSvc.add(model);

        getInstrumentation().callActivityOnResume(activity_);

        // We have to wait to ensure that the network request
        // completes before we check to see if the list is updated.
        //
        // Really...not ideal, indicates that there is a better design out
        // there. However, testing is much better than not testing even
        // with a hack!
        Thread.currentThread().sleep(1000);

        ListAdapter modelAdapter = patientList.getAdapter();

        boolean foundExpectedVideo = false;
        for(int i = 0; i < modelAdapter.getCount(); i++){
            assertTrue(modelAdapter.getItem(i) instanceof String);

            String name = (String) modelAdapter.getItem(i);
            foundExpectedVideo = foundExpectedVideo || model.getUsername().equals(name);
        }

        assertTrue(foundExpectedVideo);

        Collection<Patient> data = patientSvc.findAll();
        for(Patient m : data){
            patientSvc.delete(m.getId());
        }

        getInstrumentation().callActivityOnResume(activity_);

        // Repeat timing hack
        Thread.currentThread().sleep(1000);

        modelAdapter = patientList.getAdapter();
        assertEquals(0, modelAdapter.getCount());
    }
}
