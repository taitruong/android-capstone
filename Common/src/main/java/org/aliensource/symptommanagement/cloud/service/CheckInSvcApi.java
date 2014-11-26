package org.aliensource.symptommanagement.cloud.service;

import org.aliensource.symptommanagement.cloud.repository.CheckIn;
import org.aliensource.symptommanagement.cloud.repository.Medicament;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface CheckInSvcApi {

	public static final String SVC_PATH = "/checkIn";
    public static final String SVC_PATH_ID = SVC_PATH + "/{" + ServiceUtils.PARAMETER_ID + "}";

    @GET(SVC_PATH)
    public List<CheckIn> findAll();

    @POST(SVC_PATH)
    public CheckIn save(@Body CheckIn checkIn);

    @GET(SVC_PATH_ID)
    public CheckIn findOne(@Path(ServiceUtils.PARAMETER_ID) long id);
}
