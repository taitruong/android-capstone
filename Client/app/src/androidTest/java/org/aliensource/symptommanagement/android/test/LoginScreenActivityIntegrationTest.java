package org.aliensource.symptommanagement.android.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;

import org.aliensource.symptommanagement.android.R;

import org.aliensource.symptommanagement.android.LoginScreenActivity;
import org.aliensource.symptommanagement.android.MainActivity;
import org.aliensource.symptommanagement.cloud.TestUtils;

public class LoginScreenActivityIntegrationTest extends ActivityInstrumentationTestCase2 {

    private Button loginButton_;

    private EditText serverEditText_;

    private String localHost_;

    public LoginScreenActivityIntegrationTest() {
        super(LoginScreenActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        localHost_ = TestUtils.findTheRealLocalhostAddress();

        Activity activity = getActivity();
        loginButton_ = (Button)activity.findViewById(R.id.loginButton);
        serverEditText_ = (EditText)activity.findViewById(R.id.server);
    }

    public void testLogin() {

        // An ActivityMonitor is used to check what other Activities are launched.
        // Later, we use this ActivityMonitor to make sure that the LoginScreenActivity
        // launches the VideoListActivity.
        Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(MainActivity.class.getName(),
                null, false);

        // We can't manipulate UI views from background threads that are running
        // tests. The code in the run() method below will be invoked on the main
        // thread and can manipulate the UI.
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                serverEditText_.setText("https://" + localHost_ + ":8443");
            }
        });

        // Fake a click on the login button
        TouchUtils.clickView(this, loginButton_);

        // Now, we ensure that the LoginScreenActivity sent an Intent
        // and launched the VideoListActivity
        MainActivity receiverActivity = (MainActivity)
                am.waitForActivityWithTimeout(5000);
        assertNotNull(receiverActivity);
        assertEquals(1, am.getHits());

        // Clean up our mess...
        getInstrumentation().removeMonitor(am);
    }
}
