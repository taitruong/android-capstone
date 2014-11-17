package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.Video;
import retrofit.http.*;

import java.util.Collection;

public interface PatientSvcApi {

	public static final String TITLE_PARAMETER = "title";
	
	public static final String DURATION_PARAMETER = "duration";

    public static final String TOKEN_PATH = "/oauth/token";

	// The path where we expect the VideoSvc to live
	public static final String SVC_PATH = "/patient";

	// The path to search videos by title
	public static final String VIDEO_TITLE_SEARCH_PATH = SVC_PATH + "/search/findByName";
	
	// The path to search videos by title
	public static final String VIDEO_DURATION_SEARCH_PATH = SVC_PATH + "/search/findByDurationLessThan";

	@GET(SVC_PATH)
	public Collection<Video> getVideoList();
	
	@POST(SVC_PATH)
	public Void addVideo(@Body Video v);
	
	@DELETE(SVC_PATH + "/{id}")
	public Void deleteVideo(@Path("id") long id);
	
	@GET(VIDEO_TITLE_SEARCH_PATH)
	public Collection<Video> findByTitle(@Query(TITLE_PARAMETER) String title);
	
	@GET(VIDEO_DURATION_SEARCH_PATH)
	public Collection<Video> findByDurationLessThan(@Query(DURATION_PARAMETER) long duration);
	
}
