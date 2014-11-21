package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.Patient;

import retrofit.http.*;

import java.util.Collection;

public interface PatientSvcApi extends BaseSvcApi {

	public static final String SVC_PATH = "/patient";

	// The path to search videos by title
	public static final String SEARCH_PATH_USERNAME = SVC_PATH + "/search/findByUsername";
	
	@GET(SEARCH_PATH_USERNAME)
	public Collection<Patient> findByUsername(@Query(ServiceUtils.PARAMETER_USERNAME) String title);

    @GET(SVC_PATH)
    public Collection<Patient> findAll();

    @GET(SVC_PATH)
    public Void add(@Body Patient v);

    @DELETE(SVC_PATH + "/{id}")
    public Void delete(@Path("id") long id);

}
