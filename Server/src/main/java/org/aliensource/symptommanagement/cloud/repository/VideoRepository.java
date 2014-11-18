package org.aliensource.symptommanagement.cloud.repository;

import java.util.Collection;

import org.aliensource.symptommanagement.cloud.service.VideoSvcApi;
import org.aliensource.symptommanagement.cloud.repository.Video;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// This @RepositoryRestResource annotation tells Spring Data Rest to
// expose the VideoRepository through a controller and map it to the 
// "/video" path. This automatically enables you to do the following:
//
// 1. List all videos by sending a GET request to /video 
// 2. Add a video by sending a POST request to /video with the JSON for a video
// 3. Get a specific video by sending a GET request to /video/{videoId}
//    (e.g., /video/1 would return the JSON for the video with id=1)
// 4. Send search requests to our findByXYZ methods to /video/search/findByXYZ
//    (e.g., /video/search/findByName?title=Foo)
//
@RepositoryRestResource(path = VideoSvcApi.VIDEO_SVC_PATH)
@RequestMapping(value=VideoSvcApi.VIDEO_SVC_PATH, produces = {"application/json"})
public interface VideoRepository extends CrudRepository<Video, Long>{

    @Override
    //@PreAuthorize("hasAnyRole('ROLE_PATIENT', 'ROLE_TRUSTED_CLIENT')")
    //@PreAuthorize("principal.name.equals('xxx')")
    //@PostFilter("hasPermission(filterObject, xxxx)")
    public @ResponseBody Iterable<Video> findAll();

    // Find all videos with a matching title (e.g., Video.name)
	public Collection<Video> findByName(
			// The @Param annotation tells Spring Data Rest which HTTP request
			// parameter it should use to fill in the "title" variable used to
			// search for Videos
			@Param(VideoSvcApi.TITLE_PARAMETER) String title);
	
	// Find all videos that are shorter than a specified duration
	public Collection<Video> findByDurationLessThan(
			// The @Param annotation tells tells Spring Data Rest which HTTP request
			// parameter it should use to fill in the "duration" variable used to
			// search for Videos
			@Param(VideoSvcApi.DURATION_PARAMETER) long maxduration);
	
	/*
	 * See: http://docs.spring.io/spring-data/jpa/docs/1.3.0.RELEASE/reference/html/jpa.repositories.html 
	 * for more examples of writing query methods
	 */
	
}
