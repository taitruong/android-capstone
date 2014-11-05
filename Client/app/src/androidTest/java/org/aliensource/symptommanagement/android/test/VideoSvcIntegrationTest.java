package org.aliensource.symptommanagement.android.test;

import junit.framework.TestCase;

import org.aliensource.symptommanagement.android.VideoSvc;
import org.aliensource.symptommanagement.cloud.video.TestUtils;
import org.aliensource.symptommanagement.cloud.video.client.VideoSvcApi;
import org.aliensource.symptommanagement.cloud.video.repository.Video;

import java.util.Collection;

public class VideoSvcIntegrationTest extends TestCase {

    private String localhost_;

    private VideoSvcApi svc_;

    private final Video video_ = TestUtils.randomVideo();

    @Override
    public void setUp() throws Exception {
        localhost_ = TestUtils.findTheRealLocalhostAddress();
        assertNotNull("Is the server running? Unable to connect to the server...", localhost_);

        svc_ = VideoSvc.init(TestUtils.PROTOCOL + "://"+localhost_+":" + TestUtils.PORT,TestUtils.USER_DOCTOR1, TestUtils.USER_DOCTOR1_PASS);
        assertNotNull("This shouldn't happen...but it did...@#$!@#$", svc_);
    }

    public void testListVideos(){
        Collection<Video> videos = svc_.getVideoList();
        assertNotNull(videos);
    }

    public void testAddRemoveVideo(){
        svc_.addVideo(video_);
        Collection<Video> videos = svc_.getVideoList();
        assertTrue(videos.contains(video_));

        deleteAllVideos(videos);

        videos = svc_.getVideoList();
        assertTrue(!videos.contains(video_));
    }

    private void deleteAllVideos(Collection<Video> videos) {
        for(Video v : videos){
            svc_.deleteVideo(v.getId());
        }
    }

    public void testFindByTitle() {
        svc_.addVideo(video_);
        Collection<Video> withTitle = svc_.findByTitle(video_.getName());
        assertTrue(withTitle.size() > 0);
        for(Video v : withTitle){
            assertEquals(video_.getName(), v.getName());
        }

        String titleThatShouldntExist = video_.getName() + TestUtils.randomVideoName();
        withTitle = svc_.findByTitle(titleThatShouldntExist);
        assertTrue(withTitle.size() == 0);

        withTitle = svc_.findByTitle(video_.getName());
        deleteAllVideos(withTitle);
        withTitle = svc_.findByTitle(video_.getName());
        assertTrue(withTitle.size() == 0);
    }

    public void testFindByDurationLessThan() {
        svc_.addVideo(video_);
        long targetDuration = video_.getDuration() + 1;
        Collection<Video> withDuration = svc_.findByDurationLessThan(targetDuration);
        assertTrue(withDuration.size() > 0);
        for(Video v : withDuration){
            assertTrue(v.getDuration() < targetDuration);
        }

        long impossibleDuration = -123;
        withDuration = svc_.findByDurationLessThan(impossibleDuration);
        assertTrue(withDuration.size() == 0);

        withDuration = svc_.findByDurationLessThan(targetDuration);
        deleteAllVideos(withDuration);
        withDuration = svc_.findByDurationLessThan(targetDuration);
        assertTrue(withDuration.size() == 0);
    }


}
