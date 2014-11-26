package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.Symptom;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface SymptomSvcApi {

	public static final String SVC_PATH = "/symptom";
    public static final String SVC_PATH_ID = SVC_PATH + "/{" + ServiceUtils.PARAMETER_ID + "}";

	public static final String SEARCH_PATH_TYPE = SVC_PATH + "/search/findByType";
	
	@GET(SEARCH_PATH_TYPE)
	public List<Symptom> findByType(@Query(ServiceUtils.PARAMETER_TYPE) String type);

    @GET(SVC_PATH)
    public List<Symptom> findAll();

    @GET(SVC_PATH_ID)
    public Symptom findOne(@Path(ServiceUtils.PARAMETER_ID) long id);

    @POST(SVC_PATH)
    public Symptom save(@Body Symptom checkIn);

}
