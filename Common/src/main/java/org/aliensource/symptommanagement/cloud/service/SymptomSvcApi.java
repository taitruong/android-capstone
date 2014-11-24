package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.aliensource.symptommanagement.cloud.repository.dto.PatientDTO;
import org.aliensource.symptommanagement.cloud.repository.dto.SpringDataRestDTO;
import org.aliensource.symptommanagement.cloud.repository.dto.SymptomDTO;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface SymptomSvcApi {

	public static final String SVC_PATH = "/symptom";

	// The path to search videos by title
	public static final String SEARCH_PATH_TYPE = SVC_PATH + "/search/findByType";
	
	@GET(SEARCH_PATH_TYPE)
	public SpringDataRestDTO<SymptomDTO> findByType(@Query(ServiceUtils.PARAMETER_TYPE) String type);

    @GET(SVC_PATH)
    public SpringDataRestDTO<SymptomDTO> findAll();

}
