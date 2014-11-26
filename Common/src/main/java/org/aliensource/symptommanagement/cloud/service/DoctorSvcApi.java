package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.Doctor;

import java.util.Collection;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface DoctorSvcApi {

	public static final String SVC_PATH = "/doctor";
    public static final String SVC_PATH_ID = SVC_PATH + "/{" + ServiceUtils.PARAMETER_ID + "}";

	public static final String SEARCH_PATH_USERNAME = SVC_PATH + "/search/findByUsername";

    @GET(SVC_PATH)
    public List<Doctor> findAll();

    @POST(SVC_PATH)
    public Doctor save(@Body Doctor checkIn);

    @GET(SVC_PATH_ID)
    public Doctor findOne(@Path(ServiceUtils.PARAMETER_ID) long id);

	@GET(SEARCH_PATH_USERNAME)
	public List<Doctor> findByUsername(@Query(ServiceUtils.PARAMETER_USERNAME) String username);
	
}
