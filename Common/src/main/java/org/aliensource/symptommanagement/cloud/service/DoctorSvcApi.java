package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.Doctor;

import java.util.Collection;

import retrofit.http.GET;
import retrofit.http.Query;

public interface DoctorSvcApi {

	public static final String SVC_PATH = "/doctor";

	// The path to search videos by title
	public static final String SEARCH_PATH_USERNAME = SVC_PATH + "/search/findByUsername";
	
	@GET(SEARCH_PATH_USERNAME)
	public Doctor findByUsername(@Query(ServiceUtils.PARAMETER_USERNAME) String title);
	
}
