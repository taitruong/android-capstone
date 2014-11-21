package org.aliensource.symptommanagement.android.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.aliensource.symptommanagement.android.R;
import org.aliensource.symptommanagement.android.MainActivity;
import org.aliensource.symptommanagement.android.PatientSvc;
import org.aliensource.symptommanagement.cloud.TestUtils;
import org.aliensource.symptommanagement.cloud.repository.Video;
import org.aliensource.symptommanagement.cloud.service.VideoSvcApi;

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

    private VideoSvcApi videoSvc_;

    private MainActivity activity_;

    private ListView videoList_;

    private final Video video_ = TestUtils.randomVideo();

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        String localhost = TestUtils.findTheRealLocalhostAddress();
        assertNotNull(localhost);

        videoSvc_ = PatientSvc.init(TestUtils.PROTOCOL + "://" + localhost + ":" + TestUtils.PORT, TestUtils.USER_DOCTOR1, TestUtils.USER_DOCTOR1_PASS);
        assertNotNull(videoSvc_);

        activity_ = getActivity();
        assertNotNull(activity_);

        videoList_ = (ListView)activity_.findViewById(R.id.videoList);
        assertNotNull(videoList_);
    }

    public void testVideoListIsPopulatedCorrectly() throws Exception {
        videoSvc_.addVideo(video_);

        getInstrumentation().callActivityOnResume(activity_);

        // We have to wait to ensure that the network request
        // completes before we check to see if the list is updated.
        //
        // Really...not ideal, indicates that there is a better design out
        // there. However, testing is much better than not testing even
        // with a hack!
        Thread.currentThread().sleep(1000);

        ListAdapter videoAdapter = videoList_.getAdapter();

        boolean foundExpectedVideo = false;
        for(int i = 0; i < videoAdapter.getCount(); i++){
            assertTrue(videoAdapter.getItem(i) instanceof String);

            String name = (String)videoAdapter.getItem(i);
            foundExpectedVideo = foundExpectedVideo || video_.getName().equals(name);
        }

        assertTrue(foundExpectedVideo);

        Collection<Video> videos = videoSvc_.getVideoList();
        for(Video v : videos){
            videoSvc_.deleteVideo(v.getId());
        }

        getInstrumentation().callActivityOnResume(activity_);

        // Repeat timing hack
        Thread.currentThread().sleep(1000);

        videoAdapter = videoList_.getAdapter();
        assertEquals(0, videoAdapter.getCount());
    }
}
