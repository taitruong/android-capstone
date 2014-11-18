package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.Patient;

import retrofit.http.*;

import java.util.Collection;

public interface PatientSvcApi {

	// The path where we expect the VideoSvc to live
	public static final String SVC_PATH = "/patient";

	// The path to search videos by title
	public static final String SEARCH_PATH_USERNAME = SVC_PATH + "/search/findByUsername";
	
	@GET(SEARCH_PATH_USERNAME)
	public Collection<Patient> findByUsername(@Query(ServiceUtils.PARAMETER_USERNAME) String title);
	
}
