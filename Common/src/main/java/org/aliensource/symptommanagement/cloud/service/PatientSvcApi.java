package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.dto.PatientDTO;
import org.aliensource.symptommanagement.cloud.repository.dto.SpringDataRestDTO;

import retrofit.http.*;

public interface PatientSvcApi {

	public static final String SVC_PATH = "/patient";

	// The path to search videos by title
	public static final String SEARCH_PATH_USERNAME = SVC_PATH + "/search/findByUsername";
	
	@GET(SEARCH_PATH_USERNAME)
	public Patient findByUsername(@Query(ServiceUtils.PARAMETER_USERNAME) String username);

    @GET(SVC_PATH)
    public SpringDataRestDTO<PatientDTO> findAll();

    @POST(SVC_PATH)
    public Void add(@Body Patient v);

    @DELETE(SVC_PATH + "/{id}")
    public Void delete(@Path("id") long id);

}
