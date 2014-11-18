package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.Video;
import retrofit.http.*;

import java.util.Collection;

public interface PatientSvcApi {

	public static final String PARAMETER_USERNAME = "username";

    public static final String TOKEN_PATH = "/oauth/token";

	// The path where we expect the VideoSvc to live
	public static final String SVC_PATH = "/patient";

	// The path to search videos by title
	public static final String SEARCH_PATH_USERNAME = SVC_PATH + "/search/findByUsername";
	
	@GET(SVC_PATH)
	public Collection<Video> getVideoList();
	
	@POST(SVC_PATH)
	public Void addVideo(@Body Video v);
	
	@DELETE(SVC_PATH + "/{id}")
	public Void deleteVideo(@Path("id") long id);
	
	@GET(SEARCH_PATH_USERNAME)
	public Collection<Video> findByTitle(@Query(PARAMETER_USERNAME) String title);
	
}
